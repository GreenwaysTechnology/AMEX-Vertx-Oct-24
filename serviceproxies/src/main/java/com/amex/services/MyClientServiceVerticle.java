package com.amex.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class MyClientServiceVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //Get the Services from the proxy
    MyService myService = new ServiceProxyBuilder(vertx).setAddress("my.service.address").build(MyService.class);
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/api/hello").handler(routingContext -> {
      myService.sayHello().onSuccess(res->{
        System.out.println(res);
        routingContext.response().end(res);
      });

    });


    vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(server -> {
      System.out.println("Http server is running at " + server.actualPort());
    });    //invoke biz method


  }
}
