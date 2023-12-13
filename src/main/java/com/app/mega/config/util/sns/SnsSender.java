package com.app.mega.config.util.sns;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnsSender{
    private final AmazonSNS amazonSNS;

    public PublishResult send(SnsSenderDto senderDto) {

        PublishResult result = null;

        try {

            Map<String, MessageAttributeValue> smsAttributes =
                    new HashMap<>();

            smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                    .withStringValue("mySenderId").withDataType("String"));

            smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                    .withStringValue("0.50").withDataType("String"));

            smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                    .withStringValue("Promotional").withDataType("String"));

            result = this.sendSMSMessage(amazonSNS,
                    senderDto.getSmsTxt(),
                    senderDto.getMobileNo(),
                    smsAttributes);

        } catch (Exception ex) {

            log.error("The sms was not sent.");
            log.error("Error message: " + ex.getMessage());
            throw new AmazonClientException(ex.getMessage(), ex);
        }
        return result;
    }

    public PublishResult sendSMSMessage(AmazonSNS sns, String message , String phoneNumber,
                                        Map<String, MessageAttributeValue> smsAttributes) {

        PublishResult result = sns.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));

        return result;
    }

}