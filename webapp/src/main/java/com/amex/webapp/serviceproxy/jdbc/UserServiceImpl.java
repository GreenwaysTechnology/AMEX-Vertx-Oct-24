package com.amex.webapp.serviceproxy.jdbc;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

  private final JDBCClient jdbcClient;

  public UserServiceImpl(   Vertx vertx, JsonObject dbConfig) {
    this.jdbcClient = JDBCClient.createShared(vertx, dbConfig);
  }

  @Override
  public Future<JsonObject> getUserById(int id) {
    return Future.future(future -> jdbcClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.queryWithParams("SELECT * FROM users WHERE id = ?", new JsonArray().add(id), res -> {
          if (res.succeeded()) {
            if (res.result().getNumRows() > 0) {
              future.complete(res.result().getRows().get(0));
            } else {
              future.fail("User not found");
            }
          } else {
            future.fail(res.cause());
          }
          connection.close();
        });
      } else {
        future.fail(ar.cause());
      }
    }));
  }

  @Override
  public Future<List<JsonObject>> getAllUsers() {
    return Future.future(future -> jdbcClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.query("SELECT * FROM users", res -> {
          if (res.succeeded()) {
            future.complete(res.result().getRows());
          } else {
            future.fail(res.cause());
          }
          connection.close();
        });
      } else {
        future.fail(ar.cause());
      }
    }));
  }

  @Override
  public Future<Void> createUser(JsonObject user) {
    return Future.future(future -> jdbcClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.updateWithParams("INSERT INTO users (name, email) VALUES (?, ?)",
          new JsonArray().add(user.getString("name")).add(user.getString("email")), res -> {
            if (res.succeeded()) {
              future.complete();
            } else {
              future.fail(res.cause());
            }
            connection.close();
          });
      } else {
        future.fail(ar.cause());
      }
    }));
  }

  @Override
  public Future<Void> updateUser(int id, JsonObject user) {
    return Future.future(future -> jdbcClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.updateWithParams("UPDATE users SET name = ?, email = ? WHERE id = ?",
          new JsonArray().add(user.getString("name")).add(user.getString("email")).add(id), res -> {
            if (res.succeeded()) {
              future.complete();
            } else {
              future.fail(res.cause());
            }
            connection.close();
          });
      } else {
        future.fail(ar.cause());
      }
    }));
  }

  @Override
  public Future<Void> deleteUser(int id) {
    return  Future.future(future->jdbcClient.getConnection(ar -> {
      if (ar.succeeded()) {
        SQLConnection connection = ar.result();
        connection.updateWithParams("DELETE FROM users WHERE id = ?", new JsonArray().add(id), res -> {
          if (res.succeeded()) {
            future.complete();
          } else {
            future.fail(res.cause());
          }
          connection.close();
        });
      } else {
        future.fail(ar.cause());
      }
    }));
  }
}
