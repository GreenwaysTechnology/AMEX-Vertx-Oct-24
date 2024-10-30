package com.amex.web.communiction;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class GreeterVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", GreeterVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    //apis
    router.get("/api/greet").handler(routingContext -> {
      JsonObject message = new JsonObject().put("message", "Hello");
      routingContext.response().putHeader("content-type", "application/json").end(message.encodePrettily());
    });
    vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(server -> {
      System.out.println("HTTP Server is Running at " + server.result().actualPort());
    });
  }
}
