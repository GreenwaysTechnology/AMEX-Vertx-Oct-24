package com.amex.threads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class BlockingAndNonBlockingVerticle extends AbstractVerticle {

  private void runBlocking() {
    vertx.executeBlocking(blockingHandler -> {
      //write any blocking
      System.out.println("Blocking code runs in --->" + Thread.currentThread().getName());
      try {
        String result = "Hello,I am from Blocking";
        Thread.sleep(10000);
        blockingHandler.complete(result);
      } catch (InterruptedException es) {
        System.out.println(es.getCause().getMessage());
      }
    }, resultHandler -> {
      System.out.println("Nonblocking code runs in --->" + Thread.currentThread().getName());
      System.out.println(resultHandler.result().toString());
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    runBlocking();
  }
}
