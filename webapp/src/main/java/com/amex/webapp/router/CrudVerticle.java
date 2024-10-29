package com.amex.webapp.router;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class CrudVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", CrudVerticle.class.getName());
  }

  //InMemory store
  JsonObject store = new JsonObject();

  //biz logic
  private void getAllItems(RoutingContext rc) {
    rc.response().putHeader("content-type", "application/json").setStatusCode(200).end(store.encodePrettily());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    //Enable body handler , so that all incoming payload will be available inside Routing Context
    router.route().handler(BodyHandler.create());
    //Expose api
    router.get("/api/items").handler(this::getAllItems);
    router.post("/api/items").handler(this::createItem);
    router.put("/api/items/:id").handler(this::updateItem);
    router.delete("/api/items/:id").handler(this::removeItem);

    //Create Http server attach Router
    vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(server -> {
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
