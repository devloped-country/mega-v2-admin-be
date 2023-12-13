package com.app.mega.common;

import com.app.mega.service.jpa.DeliveryService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.model.Message;

@RestController
@RequiredArgsConstructor
public class Listener {

  private final DeliveryService deliveryService;

  @SqsListener(value = "bsdev07-queue.fifo")
  public void listen(Message message) {
    deliveryService.delivery(message);
  }
}