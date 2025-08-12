package com.ptit.taskservice.controller;

import com.ptit.taskservice.dto.response.MemberResponse;
import com.ptit.taskservice.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author long.truong
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService service;

  public MemberController(MemberService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<MemberResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

}
