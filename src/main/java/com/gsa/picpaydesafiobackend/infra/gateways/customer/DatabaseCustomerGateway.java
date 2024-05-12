package com.gsa.picpaydesafiobackend.infra.gateways.customer;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.picpaydesafiobackend.infra.dao.CustomerDAO;
import com.gsa.picpaydesafiobackend.infra.dao.orms.CustomerType;
import com.gsa.picpaydesafiobackend.application.gateways.CustomerGateway;

@Repository
public class DatabaseCustomerGateway implements CustomerGateway {
  @Autowired
  private CustomerDAO customerDAO;

  @Override
  public boolean isShopkeeper(UUID customerId) {
    var customer = this.customerDAO.findById(customerId).orElseThrow();
    return customer.type().equals(CustomerType.SHOPKEEPER);
  }

}
