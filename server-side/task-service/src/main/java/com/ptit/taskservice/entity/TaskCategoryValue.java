package com.ptit.taskservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;
/**
 * @author long.truong
 */

@Data
@Entity
@Table(
    name = "task_category_values",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_cat_value", columnNames = {"category_id", "value"}),
        @UniqueConstraint(name = "uq_cat_order", columnNames = {"category_id", "sort_order"})
    }
)
@EntityListeners(AuditingEntityListener.class)
public class TaskCategoryValue {

  @Id
  @Column(columnDefinition = "uuid", nullable = false, updatable = false)
  private UUID id;

  @PrePersist void pre() { if (id == null) id = UUID.randomUUID(); }

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private TaskCategory category;

  @Column(name = "value", nullable = false, length = 150)
  private String value;

  @Column(name = "sort_order", nullable = false)
  private int sortOrder;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;
}
