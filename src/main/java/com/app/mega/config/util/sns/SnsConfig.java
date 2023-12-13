package com.app.mega.config.util.sns;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.app.mega.config.util.AwsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnsConfig extends AwsConfig {
    @Bean
    public AmazonSNS amazonSNS() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);

        return AmazonSNSClientBuilder.standard()
                .withRegion("ap-northeast-3")
                .withCredentials(awsStaticCredentialsProvider)
                .build();
    }
}
