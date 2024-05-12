package com.gsa.picpaydesafiobackend.infra.rabbitmq.queues;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifyTransferQueue {
  @Bean
  public Queue queue() {
    return new Queue("notify-transfer", true);
  }
}
