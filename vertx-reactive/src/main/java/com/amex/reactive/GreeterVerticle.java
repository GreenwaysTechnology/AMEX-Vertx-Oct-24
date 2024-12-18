package com.amex.reactive;

import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.rxjava3.core.AbstractVerticle;
import io.vertx.rxjava3.ext.web.Router;

public class GreeterVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", GreeterVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startFuture) throws Exception {

    Router router = Router.router(vertx);
    router.get("/").handler(rc -> {
      rc.response().end("Reactive Router");
    });
    //http server with reactive
//    vertx.createHttpServer().requestHandler(rc -> {
//        rc.response().end("Hello Reactive");
//      })
//      .rxListen(8080)
//      .subscribe(server -> {
//        System.out.println(server.actualPort());
//      });
    vertx.createHttpServer().requestHandler(router)
      .rxListen(8080)
      .subscribe(server -> {
        System.out.println(server.actualPort());
      });
  }
}
