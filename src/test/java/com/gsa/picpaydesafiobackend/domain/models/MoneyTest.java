package com.gsa.picpaydesafiobackend.domain.models;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gsa.picpaydesafiobackend.domain.exceptions.MoneyCannotBeNegativeException;

public class MoneyTest {
  @Test
  void it_should_create_money() {
    var money = Money.create(BigDecimal.valueOf(230.30));
    assert money.value().equals(BigDecimal.valueOf(230.30));
  }

  @Test
  void it_should_fail_if_value_is_negative() {
    assertThrows(MoneyCannotBeNegativeException.class, () -> Money.create(BigDecimal.valueOf(-230.30)));
  }
}
