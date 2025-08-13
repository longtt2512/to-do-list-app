package com.ptit.taskservice.repository;



import com.ptit.taskservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
/**
 * @author long.truong
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
  Optional<UserProfile> findByUser_Id(UUID userId);
  boolean existsByUser_Id(UUID userId);
}
