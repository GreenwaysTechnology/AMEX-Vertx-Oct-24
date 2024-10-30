package com.amex.eventbus;

import io.vertx.core.Vertx;

public class Deployer {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ProductRestApiVerticle());
    vertx.deployVerticle(new RequestReplyVerticle());
    vertx.deployVerticle(new PointToPointVerticle());
    vertx.deployVerticle(new PubSubVerticle());
  }
}
