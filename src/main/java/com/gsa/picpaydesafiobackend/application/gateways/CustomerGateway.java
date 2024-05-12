package com.gsa.picpaydesafiobackend.application.gateways;

import java.util.UUID;

public interface CustomerGateway {
  public boolean isShopkeeper(UUID customerId);
}
