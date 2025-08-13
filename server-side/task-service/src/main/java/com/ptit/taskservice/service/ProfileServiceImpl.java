package com.ptit.taskservice.service;


import com.ptit.taskservice.dto.request.ProfileRequest;
import com.ptit.taskservice.dto.response.ProfileResponse;
import com.ptit.taskservice.entity.User;
import com.ptit.taskservice.entity.UserProfile;
import com.ptit.taskservice.repository.UserProfileRepository;
import com.ptit.taskservice.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author long.truong
 */
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

  private final UserRepository users;
  private final UserProfileRepository profiles;
  private final FileService files;
  private final Duration presignExpiry = Duration.ofMinutes(15); // tweak or inject from config

  public ProfileServiceImpl(UserRepository users, UserProfileRepository profiles, FileService files) {
    this.users = users;
    this.profiles = profiles;
    this.files = files;
  }

  @Transactional(readOnly = true)
  @Override
  public ProfileResponse getMyProfile() {
    User me = currentUser();
    UserProfile p = profiles.findByUser_Id(me.getId()).orElseGet(() -> {
      UserProfile tmp = new UserProfile();
      tmp.setUser(me);
      return tmp;
    });
    return toDto(p); // includes avatarUrl if key exists
  }

  @Override
  public ProfileResponse upsertMyProfile(ProfileRequest req) {
    User me = currentUser();
    UserProfile p = profiles.findByUser_Id(me.getId()).orElseGet(() -> {
      UserProfile np = new UserProfile();
      np.setUser(me);
      return np;
    });

    p.setFirstName(req.firstName());
    p.setLastName(req.lastName());
    p.setEmail(req.email());
    p.setContactNumber(req.contactNumber());
    p.setPosition(req.position());

    return toDto(profiles.save(p));
  }

  @Override
  public ProfileResponse uploadMyAvatar(MultipartFile file) throws IOException {
    User me = currentUser();
    UserProfile p = profiles.findByUser_Id(me.getId()).orElseGet(() -> {
      UserProfile np = new UserProfile();
      np.setUser(me);
      return np;
    });

    String original = Objects.requireNonNullElse(file.getOriginalFilename(), "file");
    String ext = FilenameUtils.getExtension(original);
    if (ext == null || ext.isBlank()) {
      String ct = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
      ext = switch (ct) {
        case "image/jpeg" -> "jpg";
        case "image/png" -> "png";
        case "image/gif" -> "gif";
        case "image/webp" -> "webp";
        default -> "";
      };
    }
    String safeBase = FilenameUtils.getBaseName(original).replaceAll("[^A-Za-z0-9._-]", "_");
    long ts = Instant.now().toEpochMilli();
    String key = ext.isEmpty()
        ? "users/%s/avatar/%d_%s".formatted(me.getId(), ts, safeBase)
        : "users/%s/avatar/%d_%s.%s".formatted(me.getId(), ts, safeBase, ext);

    String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

    // safest: byte[] so Content-Length matches exactly
    byte[] bytes = file.getBytes();
    files.upload(key, bytes, contentType, Map.of("uploadedAt", String.valueOf(ts)));

    p.setAvatarKey(key);
    profiles.save(p);

    return toDto(p); // includes fresh presigned URL
  }

  /* helpers */

  private User currentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
    }
    String username = auth.getName();
    return users.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found"));
  }

  private ProfileResponse toDto(UserProfile p) {
    String url = null;
    if (p.getAvatarKey() != null && !p.getAvatarKey().isBlank()) {
      try {
        url = files.presignGet(p.getAvatarKey(), presignExpiry).toString();
      } catch (Exception ignored) {
        // If presign fails, leave url as null (don’t break profile retrieval)
      }
    }
    return new ProfileResponse(
        p.getId(),
        p.getUser() == null ? null : p.getUser().getId(),
        p.getFirstName(),
        p.getLastName(),
        p.getEmail(),
        p.getContactNumber(),
        p.getPosition(),
        p.getAvatarKey(),
        url,                 // <-- presigned URL
        p.getCreatedAt(),
        p.getUpdatedAt(),
        p.getCreatedBy(),
        p.getUpdatedBy()
    );
  }
}
