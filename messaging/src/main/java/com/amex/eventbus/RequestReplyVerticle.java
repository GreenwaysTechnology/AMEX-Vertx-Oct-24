package com.amex.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

public class RequestReplyVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<JsonObject> consumer = eventBus.consumer(Address.PRODUCT_INFO);
    consumer.handler(message -> {
      System.out.println("I have received a message: " + message.body().encodePrettily());
      message.reply("how interesting!");
    });
  }
}
