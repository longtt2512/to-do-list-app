package com.ptit.taskservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;
/**
 * @author long.truong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
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

  @CreatedBy
  @Column(name = "created_by", nullable = false, length = 100)
  private String createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", nullable = false, length = 100)
  private String updatedBy;
}
