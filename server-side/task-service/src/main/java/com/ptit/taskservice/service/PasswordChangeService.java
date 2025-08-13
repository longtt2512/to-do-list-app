package com.ptit.taskservice.service;


import com.ptit.taskservice.dto.request.ChangePasswordRequest;
import com.ptit.taskservice.entity.User;
import com.ptit.taskservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
/**
 * @author long.truong
 */
@Service
public class PasswordChangeService {

  private final UserRepository users;
  private final PasswordEncoder encoder;

  public PasswordChangeService(UserRepository users, PasswordEncoder encoder) {
    this.users = users;
    this.encoder = encoder;
  }

  @Transactional
  public void changePassword(ChangePasswordRequest req) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
    }

    String username = auth.getName();
    User user = users.findByUsername(username)
        .orElseThrow(() -> new NoSuchElementException("User not found"));

    // 1) verify current password
    if (!encoder.matches(req.currentPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
    }

    // 2) optional confirm check
    if (req.confirmNewPassword() != null && !req.newPassword().equals(req.confirmNewPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password confirmation does not match");
    }

    // 3) reject if same as old
    if (encoder.matches(req.newPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from current password");
    }

    // TODO: add extra policy checks here if you want (e.g., regex for complexity)

    // 4) update hash
    user.setPassword(encoder.encode(req.newPassword()));
    users.save(user);

  }
}
