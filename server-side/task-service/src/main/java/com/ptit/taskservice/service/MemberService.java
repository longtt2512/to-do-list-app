package com.ptit.taskservice.service;

import com.ptit.taskservice.dto.response.MemberResponse;

import java.util.List;

/**
 * @author long.truong
 */
public interface MemberService {
  List<MemberResponse> findAll();
}
