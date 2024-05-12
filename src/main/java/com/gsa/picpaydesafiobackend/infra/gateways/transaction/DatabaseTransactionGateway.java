package com.gsa.picpaydesafiobackend.infra.gateways.transaction;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.picpaydesafiobackend.infra.dao.CustomerDAO;
import com.gsa.picpaydesafiobackend.infra.dao.TransactionDAO;
import com.gsa.picpaydesafiobackend.infra.dao.orms.TransactionORM;
import com.gsa.picpaydesafiobackend.application.gateways.TransactionGateway;
import com.gsa.picpaydesafiobackend.application.gateways.dtos.TransactionDTO;

@Repository
public class DatabaseTransactionGateway implements TransactionGateway {
  @Autowired
  private TransactionDAO transactionDAO;
  @Autowired
  private CustomerDAO customerDAO;

  @Override
  public void create(TransactionDTO transactionDTO) {
    var payerORM = this.customerDAO.findById(transactionDTO.payerId()).orElseThrow();
    var payeeORM = this.customerDAO.findById(transactionDTO.payeeId()).orElseThrow();

    var transactionORM = new TransactionORM(transactionDTO.id(), payerORM, payeeORM, transactionDTO.balance());
    this.transactionDAO.save(transactionORM);
  }

}
