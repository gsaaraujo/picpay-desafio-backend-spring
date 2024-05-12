package com.gsa.picpaydesafiobackend.infra.gateways.messagequeue;

import java.util.EventListener;

import org.springframework.stereotype.Component;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.picpaydesafiobackend.application.gateways.MessageQueueGateway;

@Component
public class RabbitMqMessageQueueGateway implements MessageQueueGateway {
  @Autowired
  private AmqpTemplate template;

  @Override
  public void publish(String name, Object message) {
    template.convertAndSend(name, message);
  }

  @Override
  public void subscribe(String name, EventListener listener) {
  }

}
