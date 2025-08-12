package com.ptit.taskservice.repository;

import com.ptit.taskservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author long.truong
 */
public interface MemberRepository extends JpaRepository<Member, UUID> {
  Optional<Member> findByCode(String code);

  boolean existsByCode(String code);

  boolean existsByEmail(String email);
}
