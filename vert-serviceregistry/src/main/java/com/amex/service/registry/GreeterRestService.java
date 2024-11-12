package com.amex.service.registry;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class GreeterRestService extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.get("/api/hello").handler(rc->{
       rc.response().end("Hello, Service Discovery");
    });
    vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(server->{
      System.out.println("Greeter Server is Running at " + server.actualPort());
    });

  }
}
