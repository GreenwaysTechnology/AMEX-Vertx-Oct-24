package com.amex.webapp.router;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


public class MultiRouterVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", MultiRouterVerticle.class.getName());
  }

  //InMemory store
  JsonObject store = new JsonObject();

  //biz logic
  private void getAllItems(RoutingContext rc) {
    rc.response().putHeader("content-type", "application/json").setStatusCode(200).end(store.encodePrettily());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    //entry router
    Router mainRouter = Router.router(vertx);

    //application specific routers
    Router itemRouter = Router.router(vertx);
    Router productsRouter = Router.router(vertx);

    //Enable body handler , so that all incoming payload will be available inside Routing Context
    mainRouter.route().handler(BodyHandler.create());
    //Expose api
    itemRouter.get("/").handler(this::getAllItems);
    itemRouter.post("/").handler(this::createItem);
    itemRouter.put("/:id").handler(this::updateItem);
    itemRouter.delete("/:id").handler(this::removeItem);

    productsRouter.get("/").handler(this::getAllItems);
    productsRouter.post("/").handler(this::createItem);
    productsRouter.put("/:id").handler(this::updateItem);
    productsRouter.delete("/:id").handler(this::removeItem);

    //connect all routers
    mainRouter.mountSubRouter("/api/items", itemRouter);
    mainRouter.mountSubRouter("/api/products", productsRouter);


    //Create Http server attach Router
    vertx.createHttpServer().requestHandler(mainRouter).listen(8080).onSuccess(server -> {
      System.out.println("Http server is running at " + server.actualPort());
    });
  }

  private void removeItem(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");
    JsonObject item = routingContext.body().asJsonObject();
    if (store.containsKey(id)) {
      store.remove(id);
      routingContext.response().setStatusCode(200).end("Item removed");

    } else {
      routingContext.response().setStatusCode(404).end("Item not Found");

    }
  }

  private void updateItem(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");
    JsonObject item = routingContext.body().asJsonObject();
    if (store.containsKey(id)) {
      store.put(id, item);
      routingContext.response().setStatusCode(200).end("Item Updated");

    } else {
      routingContext.response().setStatusCode(404).end("Item not Found");

    }
  }

  private void createItem(RoutingContext routingContext) {
    RequestBody body = routingContext.body();
    System.out.println(body.asJsonObject().encodePrettily());
    JsonObject item = body.asJsonObject();
    store.put(item.getString("id"), item);
    routingContext.response().setStatusCode(201).end("Item created");
  }

}
