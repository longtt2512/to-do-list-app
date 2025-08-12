package com.ptit.taskservice.repository;


import com.ptit.taskservice.entity.TaskCategoryValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author long.truong
 */
public interface TaskCategoryValueRepository extends JpaRepository<TaskCategoryValue, UUID> {
  List<TaskCategoryValue> findByCategoryIdOrderBySortOrderAscIdAsc(UUID categoryId);
}
