package com.amex.webapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;

public class HttpResponseHeaders extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", HttpResponseHeaders.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer()
      .requestHandler(req -> {
        req.response()
          .setStatusCode(200)
          .putHeader("content-type", "text/plain")
          .putHeader("secret", "abc23232")
          .end("Hello");
      })
      .listen(8080)
      .onSuccess(httpServer -> {
        System.out.println("Server is Running at " + httpServer.actualPort());
        startPromise.complete();
      });
  }
}
