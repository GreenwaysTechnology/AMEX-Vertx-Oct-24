package com.amex;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class FileConfigDeployer {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    //Storage Options
    ConfigStoreOptions fileStore = new ConfigStoreOptions()
      .setType("file")
      .setFormat("json")
      .setConfig(new JsonObject().put("path", "conf/config.json"));

    //Config Retriver
    ConfigRetriever configRetriever = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions().addStore(fileStore));
    configRetriever.getConfig().onSuccess(myconfig -> {
      System.out.println("Got Config");
      System.out.println(myconfig.encodePrettily());
      JsonObject config = new JsonObject().put("name", "Subramanian").mergeIn(myconfig);
      DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(config);
      vertx.deployVerticle(new ConfigFileVerticle(), deploymentOptions);
    }).onFailure(err -> {
      System.out.println("config failed to load");
      System.out.println(err.getMessage());
    });

  }
}
