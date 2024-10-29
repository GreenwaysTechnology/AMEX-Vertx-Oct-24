package com.amex.webapp.router;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class HelloWorldRouter extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", HelloWorldRouter.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //Create Router
    Router router = Router.router(vertx);
    //EXPOSE API
    router.get("/api/hello").handler(routingContext -> {
      //get response object
      routingContext.response().end("Hello");
    });
    //Create Http server attach Router
    vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(server -> {
      System.out.println("Http server is running at " + server.actualPort());
    });
  }
}
