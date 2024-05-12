package com.gsa.picpaydesafiobackend.application.gateways;

import java.util.EventListener;

public interface MessageQueueGateway {
  public void publish(String name, Object message);

  public void subscribe(String name, EventListener listener);
}
