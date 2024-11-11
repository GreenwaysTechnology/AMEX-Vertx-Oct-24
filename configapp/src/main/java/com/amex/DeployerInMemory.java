package com.amex;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class DeployerInMemory {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    DeploymentOptions options = new DeploymentOptions().setConfig(
      new JsonObject().put("message", "Hello")
    );
    vertx.deployVerticle(new InMemoryConfigVerticle(), options);
  }
}
