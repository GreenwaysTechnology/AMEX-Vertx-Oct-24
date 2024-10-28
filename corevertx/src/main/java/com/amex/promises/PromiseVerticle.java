package com.amex.promises;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;

public class PromiseVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", PromiseVerticle.class.getName());
  }

  public Promise<String> getHelloPromise() {
    Promise promise = Promise.promise();
    promise.complete("Hello Promise");
    return promise;
  }

  public Future<String> getHaiPromise() {
    Promise promise = Promise.promise();
    promise.complete("Hai Promise");
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    getHelloPromise().future().onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      }
    });
    getHelloPromise().future().onSuccess(System.out::println);

    getHaiPromise().onSuccess(System.out::println);
  }


}
