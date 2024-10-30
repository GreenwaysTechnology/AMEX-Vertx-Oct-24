package com.amex.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ProductRestApiVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.post("/api/products").handler(routingContext -> {
      JsonObject products = routingContext.body().asJsonObject();
      System.out.println(products);
      //publish message on ProductInfo address
      vertx.eventBus().request(Address.PRODUCT_INFO, products)
        .onSuccess(handler -> {
          System.out.println("Got : " + handler.body());
          routingContext.response().end(handler.body().toString());
        }).onFailure(err -> {
          System.out.println(err);
        });
    });
    router.get("/api/products/:id").handler(routingContext -> {
      String id = routingContext.pathParam("id");
      vertx.eventBus().send("point.point", id);

      routingContext.response().end("Point to Point");
    });

    router.get("/api/products/offers/:message").handler(routingContext -> {
      String message = routingContext.pathParam("message");
      vertx.eventBus().publish("pub.sub", message);
      routingContext.response().end("Pub to Sub");
    });


    vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(server -> {
      System.out.println("Http server is running at " + server.actualPort());
    });

  }
}
