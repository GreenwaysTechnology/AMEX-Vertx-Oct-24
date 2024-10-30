package com.amex.services;

import io.vertx.core.Future;

public class MyServiceImpl implements MyService{
  @Override
  public Future<String> sayHello() {
     return  Future.succeededFuture("Hello Service Proxy");
  }
}
