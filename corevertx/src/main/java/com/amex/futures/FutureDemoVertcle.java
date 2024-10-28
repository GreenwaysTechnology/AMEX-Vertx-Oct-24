package com.amex.futures;

import io.vertx.core.*;

public class FutureDemoVertcle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", FutureDemoVertcle.class.getName());
  }

  //empty Future
  public Future<Void> getEmptyFuture(){
      //Create FutureObject using Anoymous innerclass pattern
//   return   Future.future(new Handler<Promise<Void>>() {
//       @Override
//       public void handle(Promise<Void> event) {
//            event.complete();
//       }
//     });
    //Create Future Object using lambda pattern: it is highly recommend
    return   Future.future(event->{
        event.complete(); //complete method is empty meaning that no data is returned
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    //callee :anonymous inner class
//    getEmptyFuture().onComplete(new Handler<AsyncResult<Void>>() {
//      @Override
//      public void handle(AsyncResult<Void> asyncResult) {
//          if(asyncResult.succeeded()){
//            System.out.println("Empty Future returned");
//            asyncResult.result();
//          }
//      }
//    });
    getEmptyFuture().onComplete(asyncResult -> {
      if(asyncResult.succeeded()){
        System.out.println("Empty Future is returned");
      }
    });

  }

  @Override
  public void stop() throws Exception {
    super.stop();
  }
}
