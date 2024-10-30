package com.amex.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

    public static class RequestReplyVerticle extends AbstractVerticle {

      @Override
      public void start(Promise<Void> startPromise) throws Exception {

        EventBus eventBus = vertx.eventBus();
        //register listner
        MessageConsumer<JsonObject> consumer = eventBus.consumer("sample.test");
        consumer.handler(message -> {
          System.out.println("I have received a message: " + message.body());
          message.reply("Processed!");
        });
        System.out.println("deployed");
      }
    }
}
