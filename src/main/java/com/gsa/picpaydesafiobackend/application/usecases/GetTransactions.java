package com.gsa.picpaydesafiobackend.application.usecases;

import java.util.UUID;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.picpaydesafiobackend.infra.dao.TransactionDAO;

@Service
public class GetTransactions {
  public record Output(UUID transactionId, UUID payeeId, UUID payerId, BigDecimal value) {
  }

  @Autowired
  private TransactionDAO transactionDAO;

  public Output[] execute() {
    var transactionsORM = transactionDAO.findAll();

    return transactionsORM.stream().map(transactionORM -> new Output(transactionORM.id(), transactionORM.payer().id(),
        transactionORM.payee().id(), transactionORM.amount())).toArray(Output[]::new);

  }
}
