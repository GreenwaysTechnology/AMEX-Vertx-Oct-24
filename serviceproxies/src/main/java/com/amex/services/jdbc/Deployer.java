package com.amex.services.jdbc;

import io.vertx.core.Vertx;

public class Deployer {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new UserClientVerticle());
    vertx.deployVerticle(new UserServiceRegistrationVerticle());

  }
}
