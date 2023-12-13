package com.app.mega.config;

import com.app.mega.repository.AttendanceAuthRepository;
import com.app.mega.service.jpa.AwsDynamoDbService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.MessageListenerContainer;
import io.awspring.cloud.sqs.listener.SqsMessageListenerContainer;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.listener.errorhandler.ErrorHandler;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.TemplateAcknowledgementMode;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.Message;

@Configuration
@RequiredArgsConstructor
public class AwsSQSConfig {


  @Value("${spring.cloud.aws.credentials.access-key}")
  private String accessKey;

  @Value("${spring.cloud.aws.credentials.secret-key}")
  private String secretKey;

  @Value("${spring.cloud.aws.region.static}")
  private String region;

  @Bean
  SqsAsyncClient sqsAsyncClient(){
    return SqsAsyncClient
        .builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider
            .create(AwsBasicCredentials.create(accessKey, secretKey)))
        .build();
  }

  @Bean
  MessageListenerContainer<Object> listenerContainer(SqsAsyncClient sqsAsyncClient) {
    return SqsMessageListenerContainer
        .builder()
        .sqsAsyncClient(sqsAsyncClient)
        .messageListener(System.out::println)
        .queueNames("myTestQueue")
        .errorHandler(new ErrorHandler<Object>() {
          @Override
          public void handle(org.springframework.messaging.Message<Object> message, Throwable t) {
            ReflectionUtils.rethrowRuntimeException(t);
          }
        })
        .build();
    }

//  @SqsListener(value = "bsdev07-queue.fifo")
//  public void listen(Message message) {
//    System.out.println("!");
//    System.out.println(message);
//    sqsAsyncClient().
//    awsDynamoDbService.readQr();
//  }
}