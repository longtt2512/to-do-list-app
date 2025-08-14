package com.ptit.taskservice.repository;

import com.ptit.taskservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author long.truong
 */
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);
}
