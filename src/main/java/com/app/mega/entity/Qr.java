package com.app.mega.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@DynamoDBTable(tableName="bsdev07-qr-ddb")
@AllArgsConstructor
@Data
public class Qr {
  @DynamoDBHashKey
  private Long institutionId;

  @DynamoDBAttribute
  private String qr;
}
