package com.amex.async.timers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;


public class TimerWIthDataVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", TimerWIthDataVerticle.class.getName());
  }

  private void blockMe(String message) {
    System.out.println(message);
  }

  private Future<JsonObject> delay() {
    //async
    //Return Future object with data after 5000ms
    return Future.future(ar -> {
      JsonObject jsonObject = new JsonObject().put("message", "Hello");
      vertx.setTimer(5000, handler -> {
        //call future complete
        ar.complete(jsonObject);
      });
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    blockMe("start");
    delay().onSuccess(jsonObject -> {
      System.out.println(jsonObject.encodePrettily());
    });
    blockMe("stop");
  }
}
