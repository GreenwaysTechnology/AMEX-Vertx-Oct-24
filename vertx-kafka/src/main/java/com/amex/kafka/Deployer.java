package com.amex.kafka;

import com.amex.kafka.consumer.KafkaConsumerVerticle;
import com.amex.kafka.producer.KafkaProducerVerticle;
import io.vertx.core.Vertx;

public class Deployer {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new KafkaProducerVerticle());
    vertx.deployVerticle(new KafkaConsumerVerticle());
  }
}
