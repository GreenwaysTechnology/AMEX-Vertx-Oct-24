package com.amex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;

public class HelloWorldVerticle extends AbstractVerticle {
  //called after this verticle deployed on vertx engine
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Helloworld Verticle is started");
  }

  //called after undeployed from the vert engine
  @Override
  public void stop() throws Exception {
    super.stop();
  }
}
