package com.amex.service.registry;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class ConsumerVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //Registry server configuration
    ServiceDiscoveryOptions discoveryOptions = new ServiceDiscoveryOptions();
    discoveryOptions.setBackendConfiguration(new
      JsonObject()
      .put("connection", "127.0.0.1.2181")
      .put("ephemeral", true)
      .put("guaranteed", true)
      .put("basePath", "/services/my-backend")
    );
    //Create Service Registry Instance
    ServiceDiscovery discovery = ServiceDiscovery.create(vertx, discoveryOptions);

    Router router = Router.router(vertx);

    router.get("/api/greet").handler(rc -> {
      //communicate Service Registry and get HttpEndpoint Record
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "greeterApiRecord"))
        .onSuccess(webClient -> {
          //WebClient webClient = myWebClient;
          webClient.get("/api/hello").send().onSuccess(response -> {
            rc.response().end(response.bodyAsString());
          });
          //Relsease HttpEndPoint resource from the Registory
          rc.response().endHandler(ar1->{
             //remove /realse service Discovery Record
              ServiceDiscovery.releaseServiceObject(discovery,webClient);
          });
        })
        .onFailure(err -> {
          System.out.println(err.getMessage());
        });
    });


    vertx.createHttpServer().requestHandler(router).listen(8081).onSuccess(server -> {
      System.out.println("Consumer Server is Running  " + server.actualPort());

    }).onFailure(err -> {
      System.out.println("Consumer server has failed " + err.getMessage());
    });

  }
}
