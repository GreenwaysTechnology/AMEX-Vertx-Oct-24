package com.amex.threads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class RestApiAndBlockingApi extends AbstractVerticle {

  //This is only for some kind of background
  private void runBlocking() {
    vertx.executeBlocking(blockingHandler -> {
      //write any blocking
      System.out.println("Blocking code runs in --->" + Thread.currentThread().getName());
      try {
        //any blocking code can go
        String result = "Hello,I am from Blocking";
        Thread.sleep(10000);
        blockingHandler.complete(result);
      } catch (InterruptedException es) {
        System.out.println(es.getCause().getMessage());
      }
    }, resultHandler -> {
      System.out.println("Nonblocking code runs in --->" + Thread.currentThread().getName());
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.get("/api/block").blockingHandler(rc -> {
      try {
        String result = "Hello,I am from Blocking";
        Thread.sleep(10000);
        rc.response().setStatusCode(200).end(result);
      } catch (InterruptedException es) {
        System.out.println(es.getCause());
      }
    });

    vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(server -> {
      System.out.println("Server is Running at " + server.actualPort());
    });
  }
}
