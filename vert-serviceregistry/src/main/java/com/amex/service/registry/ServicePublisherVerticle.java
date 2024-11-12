package com.amex.service.registry;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class ServicePublisherVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //Registry server configuration
    ServiceDiscoveryOptions discoveryOptions = new ServiceDiscoveryOptions();
    discoveryOptions.setBackendConfiguration(new
      JsonObject()
      .put("connection","127.0.0.1.2181")
      .put("ephemeral",true)
      .put("guaranteed",true)
      .put("basePath","/services/my-backend")
    );
    //Create Service Registry Instance
    ServiceDiscovery discovery = ServiceDiscovery.create(vertx,discoveryOptions);
    //Create Record: HTTPENDPOINT- REST API INTO REGISTRY
    Record httpEndpointRecord= HttpEndpoint.createRecord("greeterApiRecord","localhost",8080,"/api/hello");
        //publish the record into service registry
    discovery.publish(httpEndpointRecord).onSuccess(record->{
      System.out.println("Sucessfully Published---->" + record.toJson());
    }).onFailure(err->{
      System.out.println("Publishing Error " + err.getMessage());
    });

  }
}
