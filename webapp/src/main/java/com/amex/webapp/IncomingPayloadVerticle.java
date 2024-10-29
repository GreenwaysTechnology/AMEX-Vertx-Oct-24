package com.amex.webapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;

public class IncomingPayloadVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", IncomingPayloadVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer()
      .requestHandler(req -> {
        req.bodyHandler(buffer -> {
          System.out.println(buffer.toJsonObject().encodePrettily());
          req.response().setStatusCode(201).end("Read");
        }).endHandler(handler -> {
          System.out.println("end handler");
        });
      })
      .listen(8080)
      .onSuccess(httpServer -> {
        System.out.println("Server is Running at " + httpServer.actualPort());
        startPromise.complete();
      });
  }
}
