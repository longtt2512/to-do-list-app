package com.ptit.taskservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.*;

import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
/**
 * @author long.truong
 */
@Service
public class S3FileService implements FileService {

  private final S3Client s3;
  private final S3Presigner presigner;
  private final String defaultBucket;
  private final Duration defaultExpiry;

  public S3FileService(S3Client s3,
                       S3Presigner presigner,
                       @Value("${minio.default-bucket}") String defaultBucket,
                       @Value("${minio.presign.expiry:PT15M}") Duration defaultExpiry) {
    this.s3 = s3;
    this.presigner = presigner;
    this.defaultBucket = defaultBucket;
    this.defaultExpiry = defaultExpiry;
  }

  /* ================= Upload ================= */

  @Override
  public void upload(String bucket, String key, byte[] data, String contentType, Map<String, String> metadata) {
    Objects.requireNonNull(data, "data");
    putObject(bucket, key, RequestBody.fromBytes(data), data.length, contentType, metadata);
  }

  @Override
  public void upload(String bucket, String key, java.io.InputStream stream, long contentLength,
                     String contentType, Map<String, String> metadata) {
    Objects.requireNonNull(stream, "stream");
    if (contentLength < 0) throw new IllegalArgumentException("contentLength must be >= 0");
    putObject(bucket, key, RequestBody.fromInputStream(stream, contentLength), contentLength, contentType, metadata);
  }

  private void putObject(String bucket, String key, RequestBody body, long contentLength,
                         String contentType, Map<String,String> metadata) {
    String ct = (contentType == null || contentType.isBlank()) ? "application/octet-stream" : contentType;
    Map<String,String> md = (metadata == null) ? Collections.emptyMap() : metadata;

    PutObjectRequest req = PutObjectRequest.builder()
        .bucket(bucket).key(key)
        .contentLength(contentLength)
        .contentType(ct)
        .metadata(md)
        .build();

    s3.putObject(req, body);
  }

  /* ================= Download ================= */

  @Override
  public DownloadedObject download(String bucket, String key) {
    GetObjectRequest req = GetObjectRequest.builder().bucket(bucket).key(key).build();
    ResponseBytes<GetObjectResponse> resp = s3.getObjectAsBytes(req);
    GetObjectResponse meta = resp.response();

    return new DownloadedObject(
        resp.asByteArray(),
        meta.contentType() == null ? "application/octet-stream" : meta.contentType(),
        meta.contentLength() == null ? resp.asByteArray().length : meta.contentLength(),
        meta.metadata() == null ? Collections.emptyMap() : meta.metadata()
    );
  }

  /* ================= Delete ================= */

  @Override
  public void delete(String bucket, String key) {
    s3.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(key).build());
  }

  /* ================= Presign ================= */

  @Override
  public URI presignGet(String bucket, String key, Duration expiry) {
    GetObjectRequest gor = GetObjectRequest.builder().bucket(bucket).key(key).build();
    GetObjectPresignRequest preq = GetObjectPresignRequest.builder()
        .signatureDuration(expiry == null ? defaultExpiry : expiry)
        .getObjectRequest(gor)
        .build();
    return presigner.presignGetObject(preq).httpRequest().getUri();
  }

  @Override
  public URI presignPut(String bucket, String key, String contentType, Duration expiry) {
    PutObjectRequest por = PutObjectRequest.builder()
        .bucket(bucket).key(key)
        .contentType(contentType == null ? "application/octet-stream" : contentType)
        .build();
    PutObjectPresignRequest preq = PutObjectPresignRequest.builder()
        .signatureDuration(expiry == null ? defaultExpiry : expiry)
        .putObjectRequest(por)
        .build();
    return presigner.presignPutObject(preq).httpRequest().getUri();
  }

  /* ================= Convenience (default bucket) ================= */

  @Override
  public void upload(String key, byte[] data, String contentType, Map<String, String> metadata) {
    upload(defaultBucket, key, data, contentType, metadata);
  }

  @Override
  public void upload(String key, java.io.InputStream stream, long contentLength, String contentType, Map<String, String> metadata) {
    upload(defaultBucket, key, stream, contentLength, contentType, metadata);
  }

  @Override
  public DownloadedObject download(String key) {
    return download(defaultBucket, key);
  }

  @Override
  public void delete(String key) {
    delete(defaultBucket, key);
  }

  @Override
  public URI presignGet(String key, Duration expiry) {
    return presignGet(defaultBucket, key, expiry);
  }

  @Override
  public URI presignPut(String key, String contentType, Duration expiry) {
    return presignPut(defaultBucket, key, contentType, expiry);
  }
}
