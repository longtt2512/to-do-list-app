package com.ptit.taskservice.service;

import com.ptit.taskservice.dto.request.*;
import com.ptit.taskservice.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author long.truong
 */
public interface ProfileService {

  ProfileResponse getMyProfile();

  ProfileResponse upsertMyProfile(ProfileRequest req);

  ProfileResponse uploadMyAvatar(MultipartFile file) throws IOException;

  SignupResponse register(SignupRequest req);
}
