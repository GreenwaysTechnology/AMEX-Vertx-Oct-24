package com.amex.promises;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;

public class PromiseStartVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", PromiseStartVerticle.class.getName());
  }

  private HttpServer httpServer;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
  //  super.start(startPromise);
    httpServer = vertx.createHttpServer().requestHandler(req -> {
      req.response().end("Hello");
    });
    //listener
    httpServer.listen(8080, (res) -> {
      if (res.succeeded()) {
        //once this code reaches this verticle considered deployed
        startPromise.complete();
        //startPromise.fail("est");
      } else {
        startPromise.fail(res.cause());
      }
    });

  }
}
