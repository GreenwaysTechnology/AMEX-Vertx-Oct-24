package com.amex.webapp.jdbc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class JdbcVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", JdbcVerticle.class.getName());
  }

  private JDBCClient jdbc;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //connect db;
    //Connection string ;
    JsonObject config = config();
    config.put("url", "jdbc:mysql://localhost:3306/mydb");
    config.put("driver_class", "com.mysql.cj.jdbc.Driver");
    config.put("user", "myuser");
    config.put("password", "mypassword");

    //create jdbcclient object
    jdbc = JDBCClient.createShared(vertx, config, "MyDataSource");

    //Routers
    Router router = Router.router(vertx);
    //body parser
    router.route().handler(BodyHandler.create());

    //CURD routes
    router.get("/api/items").handler(this::getAllItems);
    router.get("/api/items/:id").handler(this::getItem);
    router.post("/api/items").handler(this::createItem);
    router.put("/api/items/:id").handler(this::updateItem);
    router.delete("/api/items/:id").handler(this::removeItem);

    vertx.createHttpServer().requestHandler(router).listen(8080).onSuccess(server -> {
      System.out.println("HTTP server is Running" + server.actualPort());
    });

  }

  private void removeItem(RoutingContext routingContext) {
    String id = routingContext.pathParam("id");
    String DELETE_QUERY = "DELETE FROM items WHERE id=?";
    JsonArray items = new JsonArray().add(id);
    jdbc.updateWithParams(DELETE_QUERY, items, result -> {
      if (result.succeeded()) {
        routingContext.response().setStatusCode(204).end();
      } else {
        routingContext.fail(result.cause());
      }
    });

  }

  private void updateItem(RoutingContext routingContext) {
    String id = routingContext.pathParam("id");
    JsonObject body = routingContext.body().asJsonObject();
    String UPDATE_QUERY = "UPDATE items SET name= ?,description = ? WHERE id = ?";
    JsonArray items = new JsonArray()
      .add(body.getString("name"))
      .add(body.getString("description"))
      .add(id);
    jdbc.updateWithParams(UPDATE_QUERY, items, result -> {
      if (result.succeeded()) {
        routingContext.response().setStatusCode(204).end();
      } else {
        routingContext.fail(result.cause());
      }
    });
  }

  private void createItem(RoutingContext routingContext) {
    JsonObject body = routingContext.body().asJsonObject();
    String INSERT_QUERY = "INSERT INTO items (name,description) VALUES (?,?)";
    JsonArray items = new JsonArray().add(body.getString("name")).add(body.getString("description"));

    jdbc.updateWithParams(INSERT_QUERY, items, result -> {
      if (result.succeeded()) {
        routingContext.response().setStatusCode(201).end();
      } else {
        routingContext.fail(result.cause());
      }
    });
  }

  private void getItem(RoutingContext routingContext) {
    String id = routingContext.pathParam("id");
    String QUERY = "SELECT * FROM items WHERE id = ?";
    JsonArray items = new JsonArray().add(id);
    jdbc.queryWithParams(QUERY, items, result -> {
      if (result.succeeded()) {
        if (result.result().getNumRows() > 0) {
          routingContext.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(200)
            .end(result.result().getRows().get(0).toString());

        } else {
          routingContext.response().setStatusCode(404).end();
        }
      } else {
        routingContext.fail(result.cause());
      }

    });
  }

  private void getAllItems(RoutingContext routingContext) {
    String QUERY = "SELECT * FROM items";
    jdbc.query(QUERY, result -> {
      if (result.succeeded()) {
        routingContext.response().
          putHeader("content-type", "application/json")
          .end(result.result().getRows().toString());
      } else {
        routingContext.fail(result.cause());
      }
    });
  }
}
