package com.amex.async.timers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;

public class PeriodicTimerVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", PeriodicTimerVerticle.class.getName());
  }

  private void tick() {
    vertx.setPeriodic(1000, handler -> {
      System.out.println(Math.random());
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    tick();
  }
}
