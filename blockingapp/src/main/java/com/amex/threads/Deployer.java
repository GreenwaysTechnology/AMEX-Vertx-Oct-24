package com.amex.threads;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.ThreadingModel;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Deployer {
  public static void main(String[] args) {
    VertxOptions vertxOptions = new VertxOptions().setWorkerPoolSize(20);
    Vertx vertx = Vertx.vertx(vertxOptions);
//    vertx.deployVerticle(new NonBlockingVerticle());
    // vertx.deployVerticle(new TimerBlockingVerticle());

//    DeploymentOptions options= new DeploymentOptions().setWorker(true);
//    DeploymentOptions options = new DeploymentOptions().setThreadingModel(ThreadingModel.WORKER);
//    vertx.deployVerticle(new WorkerVerticle(), options);
    // vertx.deployVerticle(new BlockingAndNonBlockingVerticle());
    vertx.deployVerticle(new RestApiAndBlockingApi());
  }
}
