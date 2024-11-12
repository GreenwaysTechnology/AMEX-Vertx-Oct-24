package com.amex;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Deployer {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    DeploymentOptions options = new DeploymentOptions().setInstances(3);
//    vertx.deployVerticle(new MainVerticle(),options).onSuccess(res->{
//      System.out.println("Deployed and its Id " + res);
//    }).onFailure(err->{
//      System.out.println(err.getMessage());
//    });
    vertx.deployVerticle(MainVerticle.class.getName(), options).onSuccess(res -> {
      System.out.println("Deployed and its Id " + res);
    }).onFailure(err -> {
      System.out.println(err.getMessage());
    });
  }
}
