package com.amex.async.timers;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;


public class PeriodicTimerWithDataVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", PeriodicTimerWithDataVerticle.class.getName());
  }

  //  private Future<JsonObject> poll() {
//    return Future.future(ar -> {
//      JsonObject jsonObject = new JsonObject().put("random", Math.random());
//      vertx.setPeriodic(5000, handler -> {
//        ar.complete(jsonObject);
//      });
//    });
//  }
  private void poll(Handler<AsyncResult<JsonObject>> aHandler) {
    vertx.setPeriodic(5000, handler -> {
      JsonObject jsonObject = new JsonObject().put("random", Math.random());
      aHandler.handle(Future.succeededFuture(jsonObject));
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
//    poll().onSuccess(res -> {
//      System.out.println(res.encodePrettily());
//    });
    poll(res -> {
      System.out.println(res.result().encodePrettily());
    });
  }
}
