package com.ptit.taskservice.repository;

import com.ptit.taskservice.entity.TaskCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author long.truong
 */
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, UUID> {
  boolean existsByCategoryName(String categoryName);

  @EntityGraph(attributePaths = "values")
  Optional<TaskCategory> findWithValuesById(UUID id);
}
