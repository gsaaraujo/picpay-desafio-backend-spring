package com.gsa.picpaydesafiobackend.infra.dao.orms;

import java.util.UUID;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@Entity(name = "transactions")
public class TransactionORM {
  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "payer_id")
  private CustomerORM payer;

  @ManyToOne
  @JoinColumn(name = "payee_id")
  private CustomerORM payee;

  @Column
  private BigDecimal amount;

}