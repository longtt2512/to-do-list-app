package com.ptit.taskservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * @author long.truong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "members")
@EntityListeners(AuditingEntityListener.class)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @Column(nullable = false, length = 150)
  private String name;

  @Column(nullable = false, unique = true, length = 50)
  private String code;

  private LocalDate dob;

  // "class" is a keyword in Java; map to column "class"
  @Column(name = "class", length = 50)
  private String className;

  @Column(length = 30)
  private String phone;

  @Column(length = 150, unique = true)
  private String email;

  private String avatar;

  @JdbcTypeCode(SqlTypes.JSON)                  // Hibernate 6 JSON
  @Column(columnDefinition = "jsonb")
  private Company company = new Company();

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}
