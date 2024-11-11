package com.amex.kafka.producer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerVerticle extends AbstractVerticle {
  KafkaProducer<String, String> producer;


  public void start(Promise<Void> startPromise) throws Exception {
    Map<String, String> config = new HashMap<>();
    config.put("bootstrap.servers", "localhost:9092");
    config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    config.put("acks", "1");
    //Create Producer Object
    producer = KafkaProducer.create(vertx, config);
    KafkaProducerRecord<String, String> record = KafkaProducerRecord.create("test", "message", "hello");
    producer.write(record).onSuccess(status -> {
      System.out.println("Kafka has published Message");
      startPromise.complete();
    }).onFailure(err -> {
      System.out.println(err.getMessage());
    });
  }
}
