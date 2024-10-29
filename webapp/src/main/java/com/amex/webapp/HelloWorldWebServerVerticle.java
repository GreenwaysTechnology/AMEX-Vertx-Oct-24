package com.amex.webapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

public class HelloWorldWebServerVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", HelloWorldWebServerVerticle.class.getName());
  }

  //  private void startHttpServer() {
//    //create simple web server
//    HttpServer httpServer = vertx.createHttpServer();
//    //request handling
//    httpServer.requestHandler(request -> {
//      HttpServerResponse response = request.response();
//      //send output
//      response.end("Hello Vertx");
//    });
//
//    //start the server
//    httpServer.listen(8080, serverHandler -> {
//      System.out.println("Http Server is Running at " + serverHandler.result().actualPort());
//    });
//  }
  private void startHttpServer() {
    //create simple web server using fluent style
    vertx.createHttpServer().requestHandler(req -> {
      req.response().end("Hello");
    }).listen(8080, server -> {
      System.out.println("Http Server is Running at " + server.result().actualPort());
      
    });

  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startHttpServer();
  }
}
