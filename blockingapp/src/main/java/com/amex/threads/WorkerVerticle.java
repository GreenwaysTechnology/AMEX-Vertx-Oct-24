package com.amex.threads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class WorkerVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println(WorkerVerticle.class.getName() + " is running on " + Thread.currentThread().getName());
    System.out.println("Start");
    Thread.sleep(10000);
    System.out.println("End");
  }
}
