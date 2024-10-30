package com.amex.services;

import com.amex.services.jdbc.UserClientVerticle;
import com.amex.services.jdbc.UserServiceRegistrationVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
//    vertx.deployVerticle(new ServiceProxyRegisterationVerticle());
//    vertx.deployVerticle(new MyClientServiceVerticle());
    vertx.deployVerticle(new UserServiceRegistrationVerticle());
    vertx.deployVerticle(new UserClientVerticle());
  }
}
