package com.gsa.picpaydesafiobackend.domain.models.wallet;

import java.util.UUID;
import java.math.BigDecimal;

import com.gsa.picpaydesafiobackend.application.exceptions.PayerDoesNotHaveEnoughBalanceException;

public class Wallet {
  private final UUID id;
  private UUID customerId;
  private BigDecimal balance;

  private Wallet(UUID id, UUID customerId, BigDecimal balance) {
    this.id = id;
    this.customerId = customerId;
    this.balance = balance;
  }

  public static Wallet create(UUID customerId, BigDecimal balance) {
    return new Wallet(UUID.randomUUID(), customerId, balance);
  }

  public static Wallet reconstitute(UUID id, UUID customerId, BigDecimal balance) {
    return new Wallet(id, customerId, balance);
  }

  public void withdraw(BigDecimal value) {
    if (this.balance.compareTo(value) < 0) {
      throw new PayerDoesNotHaveEnoughBalanceException();
    }

    this.balance = this.balance.subtract(value);
  }

  public void deposit(BigDecimal value) {
    this.balance = this.balance.add(value);
  }

  public UUID id() {
    return this.id;
  }

  public UUID customerId() {
    return this.customerId;
  }

  public BigDecimal balance() {
    return this.balance;
  }

}
