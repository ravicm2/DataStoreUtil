package com.util.datastore.api;

import org.json.JSONObject;

/**
 *
 */
public interface DataStoreOperations {

    JSONObject read(String key) throws Exception;

    boolean delete(String key) throws Exception;

    <T> void create(T value, String key) throws Exception;

    <T> void create(T value, String key, long expirySeconds) throws Exception;

}
