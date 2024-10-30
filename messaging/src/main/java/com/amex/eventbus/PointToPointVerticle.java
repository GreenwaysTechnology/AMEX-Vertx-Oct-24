package com.amex.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;

public class PointToPointVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    EventBus eb = vertx.eventBus();
    eb.consumer("point.point", message -> {
      System.out.println("I have received a message: " + message.body());
    });
  }
}
