package com.amex.futures;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;

public class CallbackVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", CallbackVerticle.class.getName());
  }

  //getUser
  public Future<User> getUser() {
    System.out.println("Get User is called");
    User user = new User("admin", "admin");
    //user = null; Testing for failures
    if (user != null) {
      return Future.succeededFuture(user);
    } else {
      return Future.failedFuture("User Not Found");
    }
  }

  public Future<String> login(User user) {
    System.out.println("Login is called");
    if (user.getUserName().equals("admin") && user.getPassword().equals("admin")) {
      return Future.succeededFuture("Login is success");
    } else {
      return Future.failedFuture("Login is failed");
    }
  }

  public Future<String> showDashboard(String status) {
    System.out.println("ShowDashboard is called");
    if (status.equals("Login is success")) {
      return Future.succeededFuture("Welcome to Dashboard");
    } else {
      return Future.failedFuture("Sorry Try Again!");
    }
  }

  @Override
  public void start() throws Exception {
    super.start();
    getUser().onComplete(userHandler -> {
      if (userHandler.failed()) {
        System.out.println(userHandler.cause().getMessage());
      } else {
        User user = userHandler.result();
        login(user).onComplete(loginHandler -> {
          if (loginHandler.failed()) {
            System.out.println(loginHandler.cause().getMessage());
          } else {
            showDashboard(loginHandler.result()).onComplete(dashboardHandler -> {
              if (dashboardHandler.failed()) {
                System.out.println(dashboardHandler.cause().getMessage());
              } else {
                System.out.println(dashboardHandler.result());
              }
            });
          }
        });
      }
    });
  }

  @Override
  public void stop() throws Exception {
    super.stop();
  }
}
