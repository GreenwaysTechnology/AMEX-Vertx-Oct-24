package com.amex.futures;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;


public class FutureWithDataVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", FutureWithDataVerticle.class.getName());
  }

  //Data can be anything: string,numbers,objects, Json
  public Future<String> getHelloMessage(){
      return  Future.future(future->{
          future.complete("Hello Future");
      });
  }
  //How to return error
  public Future<String> getError(){
    return  Future.future(future->{
        future.fail("Something went wrong");
    });
  }

  //biz logic with error or success

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
    //scuccess
   getHelloMessage().onComplete(event->{
      if(event.succeeded()){
        System.out.println(event.result());
      }else{
        System.out.println(event.cause().getMessage());
      }
   });
   //error
    getError().onComplete(event->{
       if(event.failed()){
         System.out.println(event.cause());
       }
    });
    //login with success
    login("admin","admin").onComplete(event->{
        if(event.succeeded()){
          System.out.println(event.result());
        }else {
          System.out.println(event.cause());
        }
    });

    login("foo","bar").onComplete(event->{
      if(event.succeeded()){
        System.out.println(event.result());
      }else {
        System.out.println(event.cause());
      }
    });


  }

  @Override
  public void stop() throws Exception {
    super.stop();
  }
}
