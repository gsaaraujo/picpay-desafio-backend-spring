package com.gsa.picpaydesafiobackend.application.usecases;

import java.util.UUID;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.gsa.picpaydesafiobackend.infra.repositories.wallet.FakeWalletDTO;
import com.gsa.picpaydesafiobackend.infra.gateways.customer.FakeCustomerGateway;
import com.gsa.picpaydesafiobackend.infra.repositories.wallet.FakeWalletRepository;
import com.gsa.picpaydesafiobackend.infra.gateways.transaction.FakeTransactionGateway;
import com.gsa.picpaydesafiobackend.infra.gateways.messagequeue.FakeMessageQueueGateway;
import com.gsa.picpaydesafiobackend.application.exceptions.PayeeWalletNotFoundException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerWalletNotFoundException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerCannotBeShopkeeperException;
import com.gsa.picpaydesafiobackend.application.exceptions.TransactionNotAuthorizedException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerCannotTransferToHimselfException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerDoesNotHaveEnoughBalanceException;
import com.gsa.picpaydesafiobackend.infra.gateways.transferauthorizer.FakeTransferAuthorizerGateway;

public class TransferTest {
  private Transfer transfer;
  private FakeWalletRepository fakeWalletRepository;
  private FakeCustomerGateway fakeCustomerGateway;
  private FakeTransactionGateway fakeTransactionGateway;
  private FakeTransferAuthorizerGateway fakeTransferAuthorizerGateway;
  private FakeMessageQueueGateway fakeMessageQueueGateway;

  @BeforeEach
  public void beforeEach() {
    this.fakeWalletRepository = new FakeWalletRepository();
    this.fakeCustomerGateway = new FakeCustomerGateway();
    this.fakeTransactionGateway = new FakeTransactionGateway();
    this.fakeMessageQueueGateway = new FakeMessageQueueGateway();
    this.fakeTransferAuthorizerGateway = new FakeTransferAuthorizerGateway();
    this.transfer = new Transfer(this.fakeWalletRepository, this.fakeCustomerGateway, this.fakeTransactionGateway,
        this.fakeMessageQueueGateway, this.fakeTransferAuthorizerGateway);
  }

  @Test
  public void it_should_transfer() {
    this.fakeCustomerGateway.isShopkeeper = false;
    this.fakeTransferAuthorizerGateway.isAuthorized = true;
    var fakePayerWallet = new FakeWalletDTO(UUID.randomUUID(), UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        BigDecimal.valueOf(320.0));
    var fakePayeeWallet = new FakeWalletDTO(UUID.randomUUID(), UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"),
        BigDecimal.valueOf(150.0));
    this.fakeWalletRepository.fakeWallets.add(fakePayerWallet);
    this.fakeWalletRepository.fakeWallets.add(fakePayeeWallet);
    var input = new Transfer.Input(UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"),
        BigDecimal.valueOf(100.0));

    this.transfer.execute(input);

    assertEquals(1, fakeTransactionGateway.fakeTransactions.size());
    assertEquals(1, fakeMessageQueueGateway.fakeMessages.size());
    assertEquals(BigDecimal.valueOf(220.0), fakeWalletRepository.fakeWallets.get(0).balance());
    assertEquals(BigDecimal.valueOf(250.0), fakeWalletRepository.fakeWallets.get(1).balance());
  }

  @Test
  public void it_should_fail_if_transfer_is_not_authorized() {
    this.fakeTransferAuthorizerGateway.isAuthorized = false;
    var input = new Transfer.Input(UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"), BigDecimal.valueOf(100.0));

    assertThrows(TransactionNotAuthorizedException.class, () -> this.transfer.execute(input));
  }

  @Test
  public void it_should_fail_if_payer_was_not_found() {
    this.fakeTransferAuthorizerGateway.isAuthorized = true;
    var fakePayeeWallet = new FakeWalletDTO(UUID.randomUUID(), UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"),
        BigDecimal.valueOf(100.0));

    this.fakeWalletRepository.fakeWallets.add(fakePayeeWallet);
    var input = new Transfer.Input(UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"), BigDecimal.valueOf(100.0));

    assertThrows(PayerWalletNotFoundException.class, () -> this.transfer.execute(input));
  }

  @Test
  public void it_should_fail_if_payee_was_not_found() {
    this.fakeTransferAuthorizerGateway.isAuthorized = true;
    var fakePayerWallet = new FakeWalletDTO(UUID.randomUUID(), UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        BigDecimal.valueOf(100.0));
    this.fakeWalletRepository.fakeWallets.add(fakePayerWallet);
    var input = new Transfer.Input(UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"), BigDecimal.valueOf(100.0));

    assertThrows(PayeeWalletNotFoundException.class, () -> this.transfer.execute(input));
  }

  @Test
  public void it_should_fail_if_payer_and_payee_are_the_same_person() {
    var input = new Transfer.Input(UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        BigDecimal.valueOf(100.0));

    assertThrows(PayerCannotTransferToHimselfException.class, () -> this.transfer.execute(input));
  }

  @Test
  public void it_should_fail_if_payer_does_not_have_enough_balance() {
    this.fakeTransferAuthorizerGateway.isAuthorized = true;
    var fakePayerWallet = new FakeWalletDTO(UUID.randomUUID(), UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        BigDecimal.valueOf(20.0));
    var fakePayeeWallet = new FakeWalletDTO(UUID.randomUUID(), UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"),
        BigDecimal.valueOf(100.0));
    this.fakeWalletRepository.fakeWallets.add(fakePayerWallet);
    this.fakeWalletRepository.fakeWallets.add(fakePayeeWallet);
    var input = new Transfer.Input(UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"),
        BigDecimal.valueOf(100.0));

    assertThrows(PayerDoesNotHaveEnoughBalanceException.class, () -> this.transfer.execute(input));
  }

  @Test
  public void it_should_fail_if_payer_is_a_shopkeeper() {
    this.fakeCustomerGateway.isShopkeeper = true;
    this.fakeTransferAuthorizerGateway.isAuthorized = true;
    var fakePayerWallet = new FakeWalletDTO(UUID.randomUUID(), UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        BigDecimal.valueOf(20.0));
    var fakePayeeWallet = new FakeWalletDTO(UUID.randomUUID(), UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"),
        BigDecimal.valueOf(100.0));
    this.fakeWalletRepository.fakeWallets.add(fakePayerWallet);
    this.fakeWalletRepository.fakeWallets.add(fakePayeeWallet);
    var input = new Transfer.Input(UUID.fromString("221af725-093a-426b-815c-5c6a9d7f98df"),
        UUID.fromString("658a6509-c8a6-4e41-b82d-66f092055f77"),
        BigDecimal.valueOf(100.0));

    assertThrows(PayerCannotBeShopkeeperException.class, () -> this.transfer.execute(input));
  }

}
