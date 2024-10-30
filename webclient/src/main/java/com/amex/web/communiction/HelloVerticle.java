package com.amex.web.communiction;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;

public class HelloVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run" ,HelloVerticle.class.getName());
  }
  //delcare webclient
  private WebClient webClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //create instance of webclient
    webClient = WebClient.create(vertx);
    //Router configuration
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/api/hello").handler(routingContext -> {
      //invoke Greeter Rest api via web client
      webClient.get(8080, "localhost", "/api/greet")
        .send().onSuccess(buffer -> {
          routingContext.response().putHeader("content-type", "application/json").end(buffer.bodyAsString());
        }).onFailure(err -> {
          routingContext.response().end(err.getMessage());
        });
    });
    vertx.createHttpServer().requestHandler(router).listen(8081).onComplete(server -> {
      System.out.println("HTTP Server is Running at " + server.result().actualPort());
    });

  }
}
