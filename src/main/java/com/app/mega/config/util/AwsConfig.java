package com.app.mega.config.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    @Value("${aws.local-code.access-key}")
    protected String accessKey;
    @Value("${aws.local-code.secret-key}")
    protected String secretKey;
}