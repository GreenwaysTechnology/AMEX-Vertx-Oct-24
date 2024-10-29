package com.amex.async.timers;

import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;


public class TimerWithHigherOrderVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", TimerWithHigherOrderVerticle.class.getName());
  }

  private void blockMe(String message) {
    System.out.println(message);
  }

  //Higher order function syntax:
  private void getMessage(Handler<AsyncResult<JsonObject>> aHandler) {
    vertx.setTimer(5000, handler -> {
      //send json object with Future
      JsonObject jsonObject = new JsonObject().put("message", "Hello");
      aHandler.handle(Future.succeededFuture(jsonObject));
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    blockMe("start");
    getMessage(response -> {
      System.out.println(response.result().encodePrettily());
    });
    blockMe("stop");
  }
}
