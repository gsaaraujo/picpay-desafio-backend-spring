package com.gsa.picpaydesafiobackend.infra.gateways.transferauthorizer;

import com.gsa.picpaydesafiobackend.application.gateways.TransferAuthorizerGateway;

public class FakeTransferAuthorizerGateway implements TransferAuthorizerGateway {
  public boolean isAuthorized = false;

  @Override
  public boolean isAuthorized() {
    return this.isAuthorized;
  }

}
