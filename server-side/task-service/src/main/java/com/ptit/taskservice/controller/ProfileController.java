package com.ptit.taskservice.controller;


import com.ptit.taskservice.dto.request.ProfileRequest;
import com.ptit.taskservice.dto.response.ProfileResponse;
import com.ptit.taskservice.service.ProfileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author long.truong
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

  private final ProfileService service;

  public ProfileController(ProfileService service) {
    this.service = service;
  }

  @GetMapping("/me")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ProfileResponse> me() {
    return ResponseEntity.ok(service.getMyProfile());
  }

  @PutMapping("/me")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ProfileResponse> updateMe(@Valid @RequestBody ProfileRequest req) {
    return ResponseEntity.ok(service.upsertMyProfile(req));
  }

  @PostMapping(path = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ProfileResponse> uploadAvatar(@RequestPart("file") @NotNull MultipartFile file)
      throws IOException {
    return ResponseEntity.ok(service.uploadMyAvatar(file));
  }
}
