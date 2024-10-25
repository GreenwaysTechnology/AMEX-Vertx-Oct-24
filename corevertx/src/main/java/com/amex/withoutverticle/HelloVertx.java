package com.amex.withoutverticle;

import io.vertx.core.Vertx;

public class HelloVertx {
  public static void main(String[] args) {
    //vertx engine ready
    Vertx vertx = Vertx.vertx();
    System.out.println(vertx.getClass().getName());
    //write simple web server
    vertx.createHttpServer().requestHandler(req->{
       req.response().end("Hello Vertx");
    }).listen(8080);
  }
}
