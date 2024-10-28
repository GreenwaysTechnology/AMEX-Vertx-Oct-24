package com.amex.futures;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;

public class FutureListenerSyntaxVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run",FutureListenerSyntaxVerticle.class.getName());
  }

  public Future<String> getMessage(){
    return  Future.future(future->{
         future.complete("Hello");
    });
  }
  public Future<String> getError(){
    return  Future.future(future->{
      future.fail("");
    });
  }
  public Future<String> login(String userName,String password){
    if(userName.equals("admin") && password.equals("admin")){
      return  Future.future(future->{
        future.complete("Login Success");
      });
    }
    return  Future.future(future->{
      future.fail("Login is failed");
    });
  }
  @Override
  public void start() throws Exception {
    super.start();
    getMessage().onComplete(event->{
         if(event.succeeded()){
           System.out.println(event.result());
         }
    });
    //shortcut with lambda
    getMessage().onSuccess(res->{
      System.out.println(res);
    });
    //shortcut with methodReference
    getMessage().onSuccess(System.out::println);

    //errro
    getError().onFailure(err->{
      System.out.println(err);
    });
    getError().onFailure(System.err::println);

    //both success and failure
    login("admin","admin").onComplete(event->{
      if(event.succeeded()){
        System.out.println(event.result());
      }else {
        System.out.println(event.cause());
      }
    });
    login("admin","admin")
      .onSuccess(System.out::println)
      .onFailure(System.err::println);
  }
}
