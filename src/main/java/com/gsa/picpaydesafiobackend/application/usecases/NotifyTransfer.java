package com.gsa.picpaydesafiobackend.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.picpaydesafiobackend.application.gateways.TransferNotifierGateway;

@Service
public class NotifyTransfer {
  @Autowired
  private TransferNotifierGateway transferNotifierGateway;

  public void execute() {
    transferNotifierGateway.notifyTransfer();
  }

}
