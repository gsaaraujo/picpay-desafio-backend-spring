package com.gsa.picpaydesafiobackend.infra.controllers.gettransactionsbycustomerid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.gsa.picpaydesafiobackend.infra.http.responses.Ok;
import com.gsa.picpaydesafiobackend.infra.http.responses.HttpResponse;
import com.gsa.picpaydesafiobackend.application.usecases.GetTransactions;

@RestController
public class GetTransactionsController {
  @Autowired
  private GetTransactions getTransactions;

  @GetMapping("/transactions")
  public ResponseEntity<HttpResponse> handle() {
    var transactions = this.getTransactions.execute();

    var ok = new Ok(transactions);
    return ResponseEntity.ok().body(ok);
  }

}
