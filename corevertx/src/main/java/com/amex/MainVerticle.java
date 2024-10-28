package com.amex;

import io.vertx.core.AbstractVerticle;
//import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Main Vertical started");
//    Vertx vertx = Vertx.vertx();
   // vertx.deployVerticle("com.amex.HelloWorldVerticle");
   // vertx.deployVerticle(HelloWorldVerticle.class.getName());
      vertx.deployVerticle(new HelloWorldVerticle());

  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("Main Vertical stopped");

  }
}
