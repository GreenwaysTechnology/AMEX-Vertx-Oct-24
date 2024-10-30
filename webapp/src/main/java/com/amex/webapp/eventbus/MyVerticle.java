package com.amex.webapp.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class MyVerticle extends AbstractVerticle {

  @Override
  public void start() {
    EventBus eventBus = vertx.eventBus();

    // Register a consumer
    eventBus.consumer("my.address", message -> {
      System.out.println("Received message: " + message.body());
      message.reply("Message received");
    });

    // Send a message
    eventBus.request("my.address", "Hello, Event Bus!", reply -> {
      if (reply.succeeded()) {
        System.out.println("Received reply: " + reply.result().body());
      } else {
        System.err.println("Failed to receive reply: " + reply.cause());
      }
    });
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MyVerticle());
  }
}
