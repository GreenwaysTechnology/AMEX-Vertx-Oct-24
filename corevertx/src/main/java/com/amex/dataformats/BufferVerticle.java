package com.amex.dataformats;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;

public class BufferVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run",BufferVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);
    //create buffer object
    Buffer buffer = Buffer.buffer("hello");
    //add more data into buffer
    buffer.appendString("welcome");
    System.out.println(buffer);
  }
}
