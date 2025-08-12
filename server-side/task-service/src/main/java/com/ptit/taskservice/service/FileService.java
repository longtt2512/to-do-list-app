package com.ptit.taskservice.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Map;

/**
 * @author long.truong
 */
public interface FileService {
  void upload(MultipartFile file, String key) throws IOException;
  void upload(String bucket, String key, byte[] data, String contentType, Map<String, String> metadata);

  void upload(String bucket, String key, java.io.InputStream stream, long contentLength, String contentType, Map<String, String> metadata);

  DownloadedObject download(String bucket, String key);

  void delete(String bucket, String key);

  URI presignGet(String bucket, String key, Duration expiry);

  URI presignPut(String bucket, String key, String contentType, Duration expiry);

  void upload(String key, byte[] data, String contentType, Map<String, String> metadata);

  void upload(String key, java.io.InputStream stream, long contentLength, String contentType, Map<String, String> metadata);

  DownloadedObject download(String key);

  void delete(String key);

  URI presignGet(String key, Duration expiry);

  URI presignPut(String key, String contentType, Duration expiry);

  record DownloadedObject(byte[] bytes, String contentType, long contentLength, Map<String, String> metadata) {
  }
}
