package com.gsa.picpaydesafiobackend.domain.models.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class WalletTest {
  @Test
  void it_should_deposit() {
    var sut = Wallet.create(UUID.randomUUID(), BigDecimal.valueOf(230.30));

    sut.deposit(BigDecimal.valueOf(135.00));

    assertEquals(BigDecimal.valueOf(365.3).compareTo(sut.balance()), 0);
  }

  @Test
  void it_should_withdraw() {
    var sut = Wallet.create(UUID.randomUUID(), BigDecimal.valueOf(230.30));

    sut.withdraw(BigDecimal.valueOf(135.00));

    assertEquals(BigDecimal.valueOf(95.3).compareTo(sut.balance()), 0);
  }
}
