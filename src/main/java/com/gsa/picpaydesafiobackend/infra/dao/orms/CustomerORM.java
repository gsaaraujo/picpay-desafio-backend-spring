package com.gsa.picpaydesafiobackend.infra.dao.orms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.AllArgsConstructor;

import jakarta.persistence.Id;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@Entity(name = "customers")
public class CustomerORM {
  @Id
  private UUID id;

  @Column(name = "full_name")
  private String fullName;

  @Enumerated(EnumType.STRING)
  private CustomerType type;

  @Column
  private String document;

  @Column
  private String email;
}
