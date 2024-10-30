package com.amex.webapp.serviceproxy.jdbc;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class UserClientVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    UserService userService = new ServiceProxyBuilder(vertx)
      .setAddress("user.service.address")
      .build(UserService.class);

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/api/users").handler(routingContext -> {
      // Example usage
      userService.getAllUsers().onComplete(res -> {
        if (res.succeeded()) {
          System.out.println(res.result());
          routingContext.response().putHeader("content-type", "application/json").end(res.result().toString());
        } else {
          System.err.println("Failed to fetch");
        }
      });
    });
    vertx.createHttpServer().requestHandler(router).listen(8080).onComplete(server -> {
      System.out.println("server is running " + server.result().actualPort());
    });

  }
}
