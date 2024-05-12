package com.gsa.picpaydesafiobackend.infra.controllers.notifytransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.gsa.picpaydesafiobackend.application.usecases.NotifyTransfer;
import com.gsa.picpaydesafiobackend.application.gateways.dtos.TransactionDTO;

@RestController
public class NotifyTransferController {
  @Autowired
  private NotifyTransfer notifyTransfer;

  @RabbitListener(queues = "notify-transfer")
  public void handle(TransactionDTO transactionDTO) {
    this.notifyTransfer.execute();
  }
}
