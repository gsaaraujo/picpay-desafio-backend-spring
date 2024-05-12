package com.gsa.picpaydesafiobackend.application.gateways;

import com.gsa.picpaydesafiobackend.application.gateways.dtos.TransactionDTO;

public interface TransactionGateway {
  public void create(TransactionDTO transactionDTO);
}
