package com.ptit.taskservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author long.truong
 */
@Service
public class JwtService {

  private static final String CLAIM_ROLES = "roles";
  private static final String CLAIM_TYPE = "type";   // "access" | "refresh"

  private final SecretKey accessKey;
  private final SecretKey refreshKey;
  private final Duration accessTtl;
  private final Duration refreshTtl;
  private final JwtParser accessParser;
  private final JwtParser refreshParser;

  public JwtService(
      @Value("${security.jwt.access-secret-b64}") String accessSecretB64,
      @Value("${security.jwt.refresh-secret-b64}") String refreshSecretB64,
      @Value("${security.jwt.access-ttl}") Duration accessTtl,
      @Value("${security.jwt.refresh-ttl}") Duration refreshTtl
  ) {
    this.accessKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(accessSecretB64));
    this.refreshKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(refreshSecretB64));
    this.accessTtl = accessTtl;
    this.refreshTtl = refreshTtl;

    this.accessParser = Jwts.parserBuilder().setAllowedClockSkewSeconds(30).setSigningKey(accessKey).build();
    this.refreshParser = Jwts.parserBuilder().setAllowedClockSkewSeconds(30).setSigningKey(refreshKey).build();
  }

  public String generateAccess(UserDetails user) {
    Instant now = Instant.now();
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_TYPE, "access");
    claims.put(CLAIM_ROLES, user.getAuthorities()
        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getUsername())
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plus(accessTtl)))
        .signWith(accessKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefresh(UserDetails user) {
    Instant now = Instant.now();
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setId(UUID.randomUUID().toString())  // jti for rotation/blacklist
        .claim(CLAIM_TYPE, "refresh")
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plus(refreshTtl)))
        .signWith(refreshKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isAccessTokenValid(String token, UserDetails userOpt) {
    try {
      Claims c = accessParser.parseClaimsJws(token).getBody();
      if (!"access".equals(c.get(CLAIM_TYPE, String.class))) return false;
      if (userOpt != null && !Objects.equals(userOpt.getUsername(), c.getSubject())) return false;
      return c.getExpiration() != null && c.getExpiration().after(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public boolean isRefreshTokenValid(String token) {
    try {
      Claims c = refreshParser.parseClaimsJws(token).getBody();
      return "refresh".equals(c.get(CLAIM_TYPE, String.class))
          && c.getExpiration() != null
          && c.getExpiration().after(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String extractUsernameFromAccess(String token) {
    return accessParser.parseClaimsJws(token).getBody().getSubject();
  }

  public String extractUsernameFromRefresh(String token) {
    return refreshParser.parseClaimsJws(token).getBody().getSubject();
  }

  /**
   * Return authorities embedded in the access token (or empty if absent).
   */
  public List<String> extractRoles(String accessToken) {
    Claims c = accessParser.parseClaimsJws(accessToken).getBody();
    Object v = c.get(CLAIM_ROLES);
    if (v instanceof Collection<?> coll) {
      return coll.stream().map(Object::toString).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  public boolean isRefreshToken(String token) {
    try {
      return "refresh".equals(refreshParser.parseClaimsJws(token).getBody().get(CLAIM_TYPE, String.class));
    } catch (JwtException e) {
      return false;
    }
  }

  /**
   * Get refresh token JTI for rotation/blacklisting.
   */
  public Optional<String> extractRefreshJti(String token) {
    try {
      return Optional.ofNullable(refreshParser.parseClaimsJws(token).getBody().getId());
    } catch (JwtException e) {
      return Optional.empty();
    }
  }

  /* =========================
     Convenience
     ========================= */

  /**
   * Issue a fresh access token from a valid refresh token. Do your own jti check/rotation externally.
   */
  public String mintAccessFromRefresh(String refreshToken, Collection<? extends GrantedAuthority> authorities) {
    Claims rc = refreshParser.parseClaimsJws(refreshToken).getBody();
    Instant now = Instant.now();
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_TYPE, "access");
    claims.put(CLAIM_ROLES, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(rc.getSubject())
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plus(accessTtl)))
        .signWith(accessKey, SignatureAlgorithm.HS256)
        .compact();
  }
}
