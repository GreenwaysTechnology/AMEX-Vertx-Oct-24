package com.amex.webapp.serviceproxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

public class MyServiceVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    MyService myService = new MyServiceImpl();

    new ServiceBinder(vertx)
      .setAddress("my.service.address")
      .register(MyService.class, myService);

    startPromise.complete();
  }
}

