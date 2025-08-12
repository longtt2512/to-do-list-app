package com.ptit.taskservice.service;


import com.ptit.taskservice.dto.request.MemberRequest;
import com.ptit.taskservice.dto.response.MemberResponse;
import com.ptit.taskservice.entity.Member;
import com.ptit.taskservice.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * @author long.truong
 */
@Service
public class MemberServiceImpl implements MemberService {

  private final MemberRepository repo;
  private final S3FileService s3FileService;

  public MemberServiceImpl(MemberRepository repo, S3FileService s3FileService) {
    this.repo = repo;
    this.s3FileService = s3FileService;
  }


  @Override
  public List<MemberResponse> findAll() {

    return repo.findAll().stream()
        .map(member -> {
              URI uri = s3FileService.presignGet(member.getAvatar(), null);

              return new MemberResponse(
                  member.getId(),
                  member.getName(),
                  member.getCode(),
                  member.getDob(),
                  member.getClassName(),
                  member.getPhone(),
                  member.getEmail(),
                  member.getCompany(),
                  uri.toString(),
                  member.getCreatedAt(),
                  member.getUpdatedAt());
            }
        )
        .toList();
  }
}
