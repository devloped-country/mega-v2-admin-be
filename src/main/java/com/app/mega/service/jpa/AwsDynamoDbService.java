package com.app.mega.service.jpa;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.app.mega.dto.response.QrResponse;
import com.app.mega.entity.Qr;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsDynamoDbService {

  private final AmazonDynamoDBClient amazonDynamoDBClient;

  public QrResponse createQr(Long id) {
    UUID uuid4 = UUID.randomUUID();
    Qr qr = new Qr(id, uuid4.toString());
    DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);

    mapper.save(qr);

    return new QrResponse(qr);
  }

  public String readQr() {
    DynamoDB dynamoDB = new DynamoDB(amazonDynamoDBClient);
    Table table = dynamoDB.getTable("bsdev07-qr-ddb");

    QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("institutionId = :value")
        .withValueMap(new ValueMap().withInt(":value", 1));
    ItemCollection<QueryOutcome> items = table.query(querySpec);
    Iterator<Item> iterator = items.iterator();

    while(iterator.hasNext()) {
      Item item =iterator.next();
      String qr = item.getString("qr");

      return qr;
    }

    return null;
  }
}