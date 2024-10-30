package com.amex.webapp.serviceproxy;

import io.vertx.core.Future;

public class MyServiceImpl implements MyService {

  @Override
  public Future<String> sayHello(String name) {
    return Future.succeededFuture("Hello, " + name + "!");
  }
}

