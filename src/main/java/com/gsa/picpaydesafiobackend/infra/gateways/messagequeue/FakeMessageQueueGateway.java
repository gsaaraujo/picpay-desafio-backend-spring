package com.gsa.picpaydesafiobackend.infra.gateways.messagequeue;

import java.util.ArrayList;
import java.util.EventListener;

import com.gsa.picpaydesafiobackend.application.gateways.MessageQueueGateway;

public class FakeMessageQueueGateway implements MessageQueueGateway {
  public ArrayList<FakeMessage> fakeMessages = new ArrayList<>();

  @Override
  public void publish(String name, Object message) {
    fakeMessages.add(new FakeMessage());
  }

  @Override
  public void subscribe(String name, EventListener listener) {
  }

}
