package com.gsa.picpaydesafiobackend.infra.controllers.transfer;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.picpaydesafiobackend.infra.http.responses.Ok;
import com.gsa.picpaydesafiobackend.application.usecases.Transfer;
import com.gsa.picpaydesafiobackend.infra.http.responses.HttpResponse;

@RestController
public class TransferController {
  @Autowired
  public Transfer transfer;

  @PostMapping("/transfer")
  public ResponseEntity<HttpResponse> handle(@Valid @RequestBody TransferInput input) {
    var transferInput = new Transfer.Input(UUID.fromString(input.payerId()), UUID.fromString(input.payeeId()),
        BigDecimal.valueOf(input.value()));

    this.transfer.execute(transferInput);

    return ResponseEntity.ok().body(new Ok());
  }

}
