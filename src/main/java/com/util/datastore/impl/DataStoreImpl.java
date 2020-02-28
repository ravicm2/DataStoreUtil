package com.util.datastore.impl;

import com.util.datastore.api.DataStoreOperations;
import org.json.JSONObject;

import java.util.Optional;

public class DataStoreImpl implements DataStoreOperations {

	@Override
	public Optional<JSONObject> read(String key) throws Exception {
		return Optional.empty();
	}

	@Override
	public boolean delete(String key) throws Exception {
		return false;
	}

	@Override
	public <T> void create(T value, String key) throws Exception {

	}

	@Override
	public <T> void create(T value, String key, long expirySeconds) throws Exception {

	}
}
