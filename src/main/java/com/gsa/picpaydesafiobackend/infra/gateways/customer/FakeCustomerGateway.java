package com.gsa.picpaydesafiobackend.infra.gateways.customer;

import java.util.UUID;

import com.gsa.picpaydesafiobackend.application.gateways.CustomerGateway;

public class FakeCustomerGateway implements CustomerGateway {
  public boolean isShopkeeper = false;

  @Override
  public boolean isShopkeeper(UUID customerId) {
    return this.isShopkeeper;
  }

}
