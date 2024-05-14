package com.gsa.picpaydesafiobackend.domain.models.wallet;

import java.util.UUID;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gsa.picpaydesafiobackend.domain.exceptions.CannotDepositZeroMoneyException;
import com.gsa.picpaydesafiobackend.domain.exceptions.CannotWithdrawZeroMoneyException;
import com.gsa.picpaydesafiobackend.domain.exceptions.MoneyCannotBeNegativeException;

public class WalletTest {
  @Test
  void it_should_deposit() {
    var sut = Wallet.create(UUID.randomUUID(), BigDecimal.valueOf(230.30));

    sut.deposit(BigDecimal.valueOf(135.00));

    assertEquals(BigDecimal.valueOf(365.3).compareTo(sut.balance().value()), 0);
  }

  @Test
  void it_should_withdraw() {
    var sut = Wallet.create(UUID.randomUUID(), BigDecimal.valueOf(230.30));

    sut.withdraw(BigDecimal.valueOf(135.00));

    assertEquals(BigDecimal.valueOf(95.3).compareTo(sut.balance().value()), 0);
  }

  @Test
  void it_should_fail_if_deposit_with_negative_value() {
    var sut = Wallet.create(UUID.randomUUID(), BigDecimal.valueOf(230.30));

    assertThrows(MoneyCannotBeNegativeException.class, () -> sut.deposit(BigDecimal.valueOf(-50)));
  }

  @Test
  void it_should_fail_if_withdraw_negative_value() {
    var sut = Wallet.create(UUID.randomUUID(), BigDecimal.valueOf(230.30));

    assertThrows(MoneyCannotBeNegativeException.class, () -> sut.withdraw(BigDecimal.valueOf(-50)));
  }

  @Test
  void it_should_fail_if_deposit_with_zero_value() {
    var sut = Wallet.create(UUID.randomUUID(), BigDecimal.valueOf(230.30));

    assertThrows(CannotDepositZeroMoneyException.class, () -> sut.deposit(BigDecimal.valueOf(0)));
  }

  @Test
  void it_should_fail_if_withdraw_zero_value() {
    var sut = Wallet.create(UUID.randomUUID(), BigDecimal.valueOf(230.30));

    assertThrows(CannotWithdrawZeroMoneyException.class, () -> sut.withdraw(BigDecimal.valueOf(0)));
  }

}
