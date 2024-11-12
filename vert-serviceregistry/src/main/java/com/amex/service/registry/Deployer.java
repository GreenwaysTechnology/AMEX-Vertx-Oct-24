package com.amex.service.registry;

import io.vertx.core.Vertx;

public class Deployer {
  public static void main(String[] args) {
    Vertx vertx  =Vertx.vertx();
    vertx.deployVerticle(new GreeterRestService());
    vertx.deployVerticle(new ServicePublisherVerticle());
    vertx.deployVerticle(new ConsumerVerticle());
  }
}
