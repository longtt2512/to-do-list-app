package com.ptit.taskservice.security;

import com.ptit.taskservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author long.truong
 */

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";
  private final JwtService jwtService;

  // Public endpoints you want to skip (adjust as needed)
  private static final List<String> WHITELIST = List.of(
      "/auth/login",
      "/auth/refresh",
      "/actuator/**",
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/swagger-ui.html"
  );

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  public JwtAuthFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    // Skip CORS preflight
    if (HttpMethod.OPTIONS.matches(request.getMethod())) return true;

    String path = request.getRequestURI();
    return WHITELIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null && header.startsWith(BEARER_PREFIX)
        && SecurityContextHolder.getContext().getAuthentication() == null) {

      String token = header.substring(BEARER_PREFIX.length()).trim();

      // Validate token (no user lookup needed; we trust token’s subject & roles)
      if (jwtService.isAccessTokenValid(token, null)) {
        String username = jwtService.extractUsernameFromAccess(token);

        List<SimpleGrantedAuthority> authorities =
            jwtService.extractRoles(token).stream()
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        var principal = new User(username, "[N/A]", authorities);
        var authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    filterChain.doFilter(request, response);
  }
}
