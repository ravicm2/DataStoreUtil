package com.util.datastore.api;

import com.util.datastore.model.Data;

import java.util.Map;
import java.util.Optional;

public interface FileOperations {

    <T> void writeValueToFile(String filePath, T object) throws Exception;

    Optional<Map<String, Data>> readValueFromFile(String filePath) throws Exception;
}
