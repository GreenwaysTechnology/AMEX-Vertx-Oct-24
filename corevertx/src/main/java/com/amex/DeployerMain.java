package com.amex;

import io.vertx.core.Vertx;

public class DeployerMain {
  public static void main(String[] args) {
      //you can deploy
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }
}
