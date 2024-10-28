package com.amex.futures;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;

public class CompositFutureVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", CompositFutureVerticle.class.getName());
  }

  public Future<String> startDbServer() {
    System.out.println("DB Server started");
    return Future.succeededFuture("Db Server is Up");
  }

  public Future<String> startHttpServer() {
    System.out.println("HTTP server started");
    return Future.succeededFuture("HttpServer is Up");
  }

  public Future<String> startConfigServer() {
    System.out.println("Config Server started");
    return Future.succeededFuture("Config Server is Up");
  }

  @Override
  public void start() throws Exception {
    super.start();
    Future<String> dbServer = startDbServer();
    Future<String> webServer = startHttpServer();
    Future<String> configServer = startConfigServer();
    Future.all(dbServer, configServer, webServer).onComplete(ar -> {
      if (ar.succeeded()) {
        System.out.println("All servers  up");
        //do something if all servers are up
      } else {
        System.out.println(ar.cause().getMessage());
      }
    });

    Future.all(dbServer, configServer, webServer).onSuccess(System.out::println).onFailure(System.out::println);
  }
}
