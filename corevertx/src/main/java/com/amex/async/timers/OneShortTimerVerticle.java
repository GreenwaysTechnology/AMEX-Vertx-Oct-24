package com.amex.async.timers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;

public class OneShortTimerVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", OneShortTimerVerticle.class.getName());
  }

  private void blockMe(String message) {
    System.out.println(message);
  }

  private void delay() {
    //async
    vertx.setTimer(5000, handler -> {
      //this code to be executed after 5000ms
      System.out.println("I am delayed Task");
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    blockMe("start");
    delay();
    blockMe("stop");
  }
}
