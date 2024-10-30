package com.amex.webapp.serviceproxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class MyClientVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    MyService myService = new ServiceProxyBuilder(vertx)
      .setAddress("my.service.address")
      .build(MyService.class);

    myService.sayHello("World").onComplete(res -> {
      if (res.succeeded()) {
        System.out.println(res.result());
      } else {
        System.err.println("Failed to call service: " + res.cause());
      }
    });

    startPromise.complete();
  }
}
