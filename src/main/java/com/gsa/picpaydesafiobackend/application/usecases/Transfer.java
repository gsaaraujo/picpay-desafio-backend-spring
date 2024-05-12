package com.gsa.picpaydesafiobackend.application.usecases;

import java.util.UUID;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsa.picpaydesafiobackend.domain.models.wallet.Wallet;
import com.gsa.picpaydesafiobackend.application.gateways.CustomerGateway;
import com.gsa.picpaydesafiobackend.domain.models.wallet.WalletRepository;
import com.gsa.picpaydesafiobackend.application.gateways.TransactionGateway;
import com.gsa.picpaydesafiobackend.application.gateways.MessageQueueGateway;
import com.gsa.picpaydesafiobackend.application.gateways.dtos.TransactionDTO;
import com.gsa.picpaydesafiobackend.application.gateways.TransferAuthorizerGateway;
import com.gsa.picpaydesafiobackend.application.exceptions.PayeeWalletNotFoundException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerWalletNotFoundException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerCannotBeShopkeeperException;
import com.gsa.picpaydesafiobackend.application.exceptions.TransactionNotAuthorizedException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerCannotTransferToHimselfException;

@Service
@Transactional
public class Transfer {
  public record Input(UUID payerId, UUID payeeId, BigDecimal value) {
  }

  private WalletRepository walletRepository;
  private CustomerGateway customerGateway;
  private TransactionGateway transactionGateway;
  private MessageQueueGateway messageQueueGateway;
  private TransferAuthorizerGateway transferAuthorizerGateway;

  public Transfer(WalletRepository walletRepository, CustomerGateway customerGateway,
      TransactionGateway transactionGateway, MessageQueueGateway messageQueueGateway,
      TransferAuthorizerGateway transferAuthorizerGateway) {
    this.walletRepository = walletRepository;
    this.customerGateway = customerGateway;
    this.transactionGateway = transactionGateway;
    this.messageQueueGateway = messageQueueGateway;
    this.transferAuthorizerGateway = transferAuthorizerGateway;
  }

  public void execute(Input input) {
    if (input.payerId.equals(input.payeeId)) {
      throw new PayerCannotTransferToHimselfException();
    }

    Wallet payerWallet = this.walletRepository.findOneByCustomerId(input.payerId())
        .orElseThrow(() -> new PayerWalletNotFoundException());
    Wallet payeeWallet = this.walletRepository.findOneByCustomerId(input.payeeId())
        .orElseThrow(() -> new PayeeWalletNotFoundException());

    if (this.customerGateway.isShopkeeper(payerWallet.customerId())) {
      throw new PayerCannotBeShopkeeperException();
    }

    payerWallet.withdraw(input.value());
    payeeWallet.deposit(input.value());

    this.walletRepository.update(payerWallet);
    this.walletRepository.update(payeeWallet);

    var transactionDTO = new TransactionDTO(UUID.randomUUID(), payerWallet.customerId(), payeeWallet.customerId(),
        input.value());
    transactionGateway.create(transactionDTO);

    if (!this.transferAuthorizerGateway.isAuthorized()) {
      throw new TransactionNotAuthorizedException();
    }

    this.messageQueueGateway.publish("notify-transfer", transactionDTO);
  }
}
