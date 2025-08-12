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
import java.time.LocalDateTime;
import java.util.UUID;
/**
/**
 * @author long.truong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "task_category_selections",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_task_value", columnNames = {"task_id", "value_id"})
    }
)
@EntityListeners(AuditingEntityListener.class)
public class TaskCategorySelection {

  @Id
  @Column(columnDefinition = "uuid", updatable = false, nullable = false)
  private UUID id;

  @PrePersist void pre() { if (id == null) id = UUID.randomUUID(); }

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "task_id")
  private Task task;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id")
  private TaskCategory category;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "value_id")
  private TaskCategoryValue value;

  @CreatedDate @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @CreatedBy
  @Column(name = "created_by", nullable = false, length = 100)
  private String createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", nullable = false, length = 100)
  private String updatedBy;
}
