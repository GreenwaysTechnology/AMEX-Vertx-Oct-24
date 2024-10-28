  package com.amex.futures;

  import io.vertx.core.AbstractVerticle;
  import io.vertx.core.Future;
  import io.vertx.core.Launcher;

  public class CallbackHellSolutionVerticle extends AbstractVerticle {
    public static void main(String[] args) {
      Launcher.executeCommand("run", CallbackHellSolutionVerticle.class.getName());
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

    private Future<String> showDashboard(String status) {
      System.out.println("ShowDashboard is called");
      if (status.equals("Login is success")) {
        return Future.succeededFuture("Welcome to Dashboard");
      } else {
        return Future.failedFuture("Sorry Try Again!");
      }
    }

    private void giveSolutionOne() {
      //Using onSuccess and OnSuccess
      getUser()
        .onSuccess(user -> {
          login(user).onSuccess(status -> {
            showDashboard(status).onSuccess(dashboard -> {
            }).onFailure(dashboardErr -> {
              System.out.println(dashboardErr.getMessage());
            });
          }).onFailure(loginErr -> {
            System.out.println(loginErr.getMessage());
          });
        }).onFailure(err -> {
          System.out.println(err.getMessage());
        });
    }

    //  private void giveSolutionTwo() {
  //    getUser()
  //      .compose(user -> {
  //        return login(user);
  //      })
  //      .compose(status -> {
  //        return showDashboard(status);
  //      })
  //      .onSuccess(res -> System.out.println(res))
  //      .onFailure(err -> System.out.println(err));
  //  }
  //  private void giveSolutionTwo() {
  //    getUser()
  //      .compose(user -> login(user))
  //      .compose(status -> showDashboard(status))
  //      .onSuccess(res -> System.out.println(res))
  //      .onFailure(err -> System.out.println(err));
  //  }

    private void giveSolutionTwo() {
      getUser()
        .compose(this::login)
        .compose(this::showDashboard)
        .onSuccess(System.out::println)
        .onFailure(System.out::println);
       }

    @Override
    public void start() throws Exception {
      super.start();
      //giveSolutionOne();
      giveSolutionTwo();
    }

    @Override
    public void stop() throws Exception {
      super.stop();
    }
  }
