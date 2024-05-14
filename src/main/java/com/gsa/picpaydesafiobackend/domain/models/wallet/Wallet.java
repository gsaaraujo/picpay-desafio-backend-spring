package com.gsa.picpaydesafiobackend.domain.models.wallet;

import java.util.UUID;
import java.math.BigDecimal;

import com.gsa.picpaydesafiobackend.domain.models.Money;
import com.gsa.picpaydesafiobackend.domain.exceptions.CannotDepositZeroMoneyException;
import com.gsa.picpaydesafiobackend.domain.exceptions.CannotWithdrawZeroMoneyException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerDoesNotHaveEnoughBalanceException;

public class Wallet {
  private final UUID id;
  private UUID customerId;
  private Money balance;

  private Wallet(UUID id, UUID customerId, Money balance) {
    this.id = id;
    this.customerId = customerId;
    this.balance = balance;
  }

  public static Wallet create(UUID customerId, BigDecimal balance) {
    var money = Money.create(balance);
    return new Wallet(UUID.randomUUID(), customerId, money);
  }

  public static Wallet reconstitute(UUID id, UUID customerId, BigDecimal balance) {
    var money = Money.reconstitute(balance);
    return new Wallet(id, customerId, money);
  }

  public void deposit(BigDecimal value) {
    if (value.compareTo(BigDecimal.ZERO) == 0) {
      throw new CannotDepositZeroMoneyException();
    }

    var money = Money.create(value);
    this.balance = Money.create(this.balance.value().add(money.value()));
  }

  public void withdraw(BigDecimal value) {
    if (value.compareTo(BigDecimal.ZERO) == 0) {
      throw new CannotWithdrawZeroMoneyException();
    }

    var money = Money.create(value);

    if (this.balance.value().compareTo(money.value()) < 0) {
      throw new PayerDoesNotHaveEnoughBalanceException();
    }

    this.balance = Money.create(this.balance.value().subtract(money.value()));
  }

  public UUID id() {
    return this.id;
  }

  public UUID customerId() {
    return this.customerId;
  }

  public Money balance() {
    return this.balance;
  }

}
