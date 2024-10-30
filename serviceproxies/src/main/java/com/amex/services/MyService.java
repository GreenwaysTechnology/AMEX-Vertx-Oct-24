package com.amex.services;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;


@VertxGen
@ProxyGen
public interface MyService {
  //apis: always must be async: either you have to with handler or future
  Future<String> sayHello();
}
