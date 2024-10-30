package com.amex.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;

public class PubSubVerticle extends AbstractVerticle {

  private void subscriberOne() {
    EventBus eb = vertx.eventBus();
    eb.consumer("pub.sub", message -> {
      System.out.println("I have received a message: " + message.body());
    });
  }

  private void subscriberTwo() {
    EventBus eb = vertx.eventBus();
    eb.consumer("pub.sub", message -> {
      System.out.println("I have received a message: " + message.body());
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    subscriberOne();
    subscriberTwo();
  }
}
