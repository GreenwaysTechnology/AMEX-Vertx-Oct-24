package com.amex.threads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class TimerBlockingVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("Start");
    //pause the current thread
    //Thread.sleep(1000);
   // Thread.sleep(2000);
    //Thread.sleep(3000);
    Thread.sleep(10000);
    System.out.println("End");
  }
}
