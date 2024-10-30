package com.amex.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

public class ServiceProxyRegisterationVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //service registration :MyService
    MyService myService = new MyServiceImpl();
    new ServiceBinder(vertx).setAddress("my.service.address").register(MyService.class, myService);
    //Next Service
  }
}
