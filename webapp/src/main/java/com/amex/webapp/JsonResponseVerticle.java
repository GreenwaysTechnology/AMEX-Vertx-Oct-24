package com.amex.webapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class JsonResponseVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", JsonResponseVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    JsonObject jsonObject = new JsonObject().put("message", "Hello");

    vertx.createHttpServer()
      .requestHandler(req -> {
        req.response()
          .setStatusCode(200)
          .putHeader("content-type", "application/json")
          .end(jsonObject.encodePrettily());
      })
      .listen(8080)
      .onSuccess(httpServer -> {
        System.out.println("Server is Running at " + httpServer.actualPort());
        startPromise.complete();
      });
  }
}
