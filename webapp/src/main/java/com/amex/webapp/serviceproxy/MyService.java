package com.amex.webapp.serviceproxy;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;

@ProxyGen
@VertxGen
public interface MyService {
  Future<String> sayHello(String name);
}

