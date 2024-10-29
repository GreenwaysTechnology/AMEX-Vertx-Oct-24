package com.amex;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
//import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start();
    System.out.println("Main Vertical started");
//    Vertx vertx = Vertx.vertx();
    // vertx.deployVerticle("com.amex.HelloWorldVerticle");
    // vertx.deployVerticle(HelloWorldVerticle.class.getName());
    // vertx.deployVerticle(new HelloWorldVerticle());



  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("Main Vertical stopped");

  }
}
