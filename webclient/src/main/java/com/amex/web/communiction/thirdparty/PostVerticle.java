package com.amex.web.communiction.thirdparty;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;

public class PostVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", PostVerticle.class.getName());
  }

  //delcare webclient
  private WebClient webClient;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    webClient = WebClient.create(vertx);
    //Router configuration
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/api/posts").handler(routingContext -> {
      //invoke Greeter Rest api via web client
      String url = "https://jsonplaceholder.typicode.com/posts";
      webClient.getAbs(url).send().onSuccess(response -> {
        routingContext.response().putHeader("content-type", "application/json").end(response.bodyAsString());
      }).onFailure(err -> {

      });

    });
    vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(server -> {
      System.out.println("HTTP Server is Running at " + server.result().actualPort());
    });

  }
}
