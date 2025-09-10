package com.ptit.taskservice.repository;


import com.ptit.taskservice.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author long.truong
 */
public interface TaskRepository extends JpaRepository<Task, UUID> {

  @EntityGraph(attributePaths = {"selections", "selections.category", "selections.value"})
  Optional<Task> findWithSelectionsById(UUID id);

  @EntityGraph(attributePaths = {"selections", "selections.category", "selections.value"})
  Page<Task> findAllBy(Pageable pageable);
}
