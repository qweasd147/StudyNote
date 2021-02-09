package com.example.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class RabbitMqExceptionHandler extends RejectAndDontRequeueRecoverer {

  @Override
  public void recover(Message message, Throwable cause) {

    final byte[] body = message.getBody();

    Map<String, Object> header = message.getMessageProperties().getHeaders();

    final String msg = new String(body, StandardCharsets.UTF_8);

    log.error("처리못한 메세지 header - {} - body {}", header, msg);
    //log.error("에러 트레이스는 너무 기니까", cause);
  }

}
