package com.ptit.taskservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author long.truong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
  private String name;
  private String url;
}
