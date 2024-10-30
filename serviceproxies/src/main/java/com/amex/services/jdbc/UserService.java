package com.amex.services.jdbc;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import java.util.List;

@ProxyGen
@VertxGen
public interface UserService {
  Future<JsonObject> getUserById(int id);
  Future<List<JsonObject>> getAllUsers();
  Future<Void> createUser(JsonObject user);
  Future<Void> updateUser(int id, JsonObject user);
  Future<Void> deleteUser(int id);
}

