package com.gsa.picpaydesafiobackend.infra.gateways.transferauthorizer;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.gsa.picpaydesafiobackend.application.gateways.TransferAuthorizerGateway;

@Component
public class HttpTransferAuthorizerGateway implements TransferAuthorizerGateway {
  public record Response(boolean isAuthorized) {
  }

  @Override
  public boolean isAuthorized() {
    var customClient = RestClient.create();

    var response = customClient.get().uri("https://run.mocky.io/v3/50032930-b573-47d8-9815-b2d3e874c773").retrieve()
        .body(Response.class);

    return response.isAuthorized();
  }

}
