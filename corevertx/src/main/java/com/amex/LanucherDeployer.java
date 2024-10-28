package com.amex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;

public class LanucherDeployer extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run",LanucherDeployer.class.getName());
  }

  @Override
  public void start() throws Exception {
    super.start();
      vertx.deployVerticle(MainVerticle.class.getName());
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("Launcher stop");
  }
}
