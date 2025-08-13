package com.ptit.taskservice.controller;

import com.ptit.taskservice.dto.request.ChangePasswordRequest;
import com.ptit.taskservice.dto.request.LoginRequest;
import com.ptit.taskservice.dto.request.RefreshTokenRequest;
import com.ptit.taskservice.dto.response.TokenPair;
import com.ptit.taskservice.service.JwtService;
import com.ptit.taskservice.service.PasswordChangeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author long.truong
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authManager;
  private final JwtService jwt;
  private final UserDetailsService userDetailsService;
  private final PasswordChangeService passwordChangeService;

  public AuthController(AuthenticationManager authManager, JwtService jwt, UserDetailsService userDetailsService, PasswordChangeService passwordChangeService) {
    this.authManager = authManager;
    this.jwt = jwt;
    this.userDetailsService = userDetailsService;
    this.passwordChangeService = passwordChangeService;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenPair> login(@RequestBody LoginRequest req) {
    var auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.username(), req.password()));
    var user = (UserDetails) auth.getPrincipal();
    String access = jwt.generateAccess(user);
    String refresh = jwt.generateRefresh(user);
    return ResponseEntity.ok(new TokenPair(access, refresh));
  }

  @PostMapping("/refresh")
  public TokenPair refresh(@RequestBody RefreshTokenRequest body) {
    String refreshToken = body.refreshToken();
    if (!jwt.isRefreshTokenValid(refreshToken)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }
    // Get username from refresh token, load user to ensure still active + get current roles
    String username = jwt.extractUsernameFromRefresh(refreshToken);
    UserDetails user = userDetailsService.loadUserByUsername(username);

    String newAccess = jwt.generateAccess(user);
    String newRefresh = jwt.generateRefresh(user); // simple: issue a fresh refresh too
    return new TokenPair(newAccess, newRefresh);
  }

  @PostMapping("/change-password")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest req) {
    passwordChangeService.changePassword(req);
    return ResponseEntity.noContent().build(); // 204
  }
}
