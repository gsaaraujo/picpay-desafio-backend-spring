package com.gsa.picpaydesafiobackend.domain.models;

import java.math.BigDecimal;

import com.gsa.picpaydesafiobackend.domain.exceptions.MoneyCannotBeNegativeException;

public class Money {
  private BigDecimal value;

  private Money(BigDecimal value) {
    this.value = value;
  }

  public static Money create(BigDecimal value) {
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new MoneyCannotBeNegativeException();
    }

    return new Money(value);
  }

  public static Money reconstitute(BigDecimal value) {
    return new Money(value);
  }

  public BigDecimal value() {
    return this.value;
  }
}
