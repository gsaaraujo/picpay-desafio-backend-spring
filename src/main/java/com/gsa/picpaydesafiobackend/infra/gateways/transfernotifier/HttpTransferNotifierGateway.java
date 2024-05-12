package com.gsa.picpaydesafiobackend.infra.gateways.transfernotifier;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.gsa.picpaydesafiobackend.application.gateways.TransferNotifierGateway;

@Component
public class HttpTransferNotifierGateway implements TransferNotifierGateway {
  public record Response(boolean isAuthorized) {
  }

  @Override
  public void notifyTransfer() {
    var customClient = RestClient.create();
    customClient.post().uri("https://run.mocky.io/v3/abaaca69-eb58-430d-8f9a-a35923e93618").retrieve()
        .body(Response.class);
  }

}
