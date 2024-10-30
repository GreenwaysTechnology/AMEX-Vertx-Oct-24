package com.amex.webapp.serviceproxy;

import io.vertx.core.Vertx;

public class Deployer {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MyServiceVerticle());
    vertx.deployVerticle(new MyClientVerticle());
  }
}
