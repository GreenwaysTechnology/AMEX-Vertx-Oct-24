package com.amex.services.jdbc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;

import io.vertx.core.json.JsonObject;

public class UserServiceRegistrationVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) {

    JsonObject dbConfig = config();
    dbConfig.put("url", "jdbc:mysql://localhost:3306/mydb");
    dbConfig.put("driver_class", "com.mysql.cj.jdbc.Driver");
    dbConfig.put("user", "myuser");
    dbConfig.put("password", "mypassword");

    UserService userService = new UserServiceImpl(vertx, dbConfig);

    new ServiceBinder(vertx).setAddress("user.service.address").register(UserService.class, userService);
    startPromise.complete();
  }
}
