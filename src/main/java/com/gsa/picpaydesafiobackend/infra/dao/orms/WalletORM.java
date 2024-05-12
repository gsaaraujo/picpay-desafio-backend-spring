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
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@Entity(name = "wallets")
public class WalletORM {
  @Id
  private UUID id;

  @OneToOne
  @JoinColumn(name = "customer_id")
  private CustomerORM customer;

  @Column
  private BigDecimal balance;
}
