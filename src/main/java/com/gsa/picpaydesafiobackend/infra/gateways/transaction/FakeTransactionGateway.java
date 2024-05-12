package com.gsa.picpaydesafiobackend.infra.gateways.transaction;

import java.util.ArrayList;

import com.gsa.picpaydesafiobackend.application.gateways.TransactionGateway;
import com.gsa.picpaydesafiobackend.application.gateways.dtos.TransactionDTO;

public class FakeTransactionGateway implements TransactionGateway {
  public ArrayList<FakeTransactionDTO> fakeTransactions = new ArrayList<>();

  @Override
  public void create(TransactionDTO transactionDTO) {
    var fakeTransactionDTO = new FakeTransactionDTO(transactionDTO.id());
    this.fakeTransactions.add(fakeTransactionDTO);
  }

}
