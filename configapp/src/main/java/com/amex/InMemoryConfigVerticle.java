package com.amex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class InMemoryConfigVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("starting");
    JsonObject myconfig = config();
    System.out.println(myconfig.encodePrettily());
  }
}
