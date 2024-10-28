package com.amex;

import io.vertx.core.AbstractVerticle;

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
    System.out.println("HelloWorld  Vertical stopped");

  }


}
