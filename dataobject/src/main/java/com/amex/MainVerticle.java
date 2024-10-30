package com.amex;

import com.amex.dto.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    User user = new User("Subramanian", 1, "sasubramanian_md@hotmail.com");
    //Convert user to Json
    JsonObject json = user.toJson();
    System.out.println(json.encodePrettily());
    //Convert json to User
    User newUser = new User(json);
    System.out.println(newUser.getId() + " " + newUser.getName() + " " + newUser.getEmail());

    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888).onComplete(http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
