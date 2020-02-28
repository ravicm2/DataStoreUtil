package com.util.datastore.api;

import org.json.JSONObject;

import java.util.Optional;

/**
 *
 */
public interface DataStoreOperations {

    Optional<JSONObject> read(String key) throws Exception;

    boolean delete(String key) throws Exception;

    <T> void create(T value, String key) throws Exception;

    <T> void create(T value, String key, long expirySeconds) throws Exception;

}
