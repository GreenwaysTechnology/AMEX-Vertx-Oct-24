package com.amex.dataformats;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class JsonVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Launcher.executeCommand("run", JsonVerticle.class.getName());
  }

  //simple json object
  private void createSimpleJson() {
    JsonObject profile = new JsonObject();
    //adding properties
    profile.put("id", 1);
    profile.put("name", "Subramanian");
    profile.put("status", true);
    System.out.println("Id : " + profile.getString("id"));
    System.out.println("Name : " + profile.getString("name"));
    System.out.println("Status : " + profile.getString("status"));
    int id = profile.getInteger("id");
    String name = profile.getString("name");
    boolean status = profile.getBoolean("status");
    System.out.println(id + " " + name + "" + status);
    //key value pair in json format
    System.out.println(profile.encodePrettily());
  }

  private void createFluentJson() {
    JsonObject profile = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true);
    System.out.println(profile.encodePrettily());
  }

  //nested json
  private void createNestedJson() {
    JsonObject profile = new JsonObject().put("id", 1).put("name", "Subramanian").put("status", true).put("address", new JsonObject().put("street", "10th street").put("city", "Coimbatore"));
    System.out.println(profile.encodePrettily());
  }

  //Array of json
  private void createJsonArray() {
    JsonObject profile = new JsonObject().put("id", 1).put("name", "Subramanian").put("status", true).put("address", new JsonObject().put("street", "10th street").put("city", "Coimbatore"));
    JsonArray profiles = new JsonArray()
      .add(profile)
      .add(new JsonObject().put("id", 2).put("name", "Murugan").put("status", false).put("address", new JsonObject().put("street", "8th street").put("city", "Chennai")));
    System.out.println(profiles.encodePrettily());
  }

  private void mergeJson(JsonObject jsonObject) {
    JsonObject config = new JsonObject().put("http.port", 8080).mergeIn(jsonObject);
    System.out.println(config.encodePrettily());
  }

  public Future<JsonObject> getJsonFuture() {
    JsonObject profile = new JsonObject().put("id", 1).put("name", "Subramanian").put("status", true).put("address", new JsonObject().put("street", "10th street").put("city", "Coimbatore"));
    return Future.succeededFuture(profile);
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // createSimpleJson();
    //createFluentJson();
    //createNestedJson();
    // createJsonArray();
    //mergeJson(new JsonObject().put("host", "localhost").put("ssl", true));
    getJsonFuture().onSuccess(json -> {
      System.out.println(json.encodePrettily());
    });
  }
}
