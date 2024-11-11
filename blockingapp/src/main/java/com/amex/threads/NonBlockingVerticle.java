package com.amex.threads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class NonBlockingVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println(NonBlockingVerticle.class.getName() + " is running on " + Thread.currentThread().getName());
  }
}
