package com.amex.webapp.serviceproxy.jdbc;

import io.vertx.core.Vertx;

public class Deployer {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new UserServiceVerticle());
    vertx.deployVerticle(new UserClientVerticle());
  }
}
