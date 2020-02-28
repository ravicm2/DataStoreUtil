package com.util.datastore.impl;

import com.util.datastore.api.FileOperations;
import com.util.datastore.model.Data;

import java.util.Map;
import java.util.Optional;

public class FileOperationsImpl implements FileOperations {
    @Override
    public <T> void writeValueToFile(String filePath, T object) throws Exception {

    }

    @Override
    public Optional<Map<String, Data>> readValueFromFile(String filePath) throws Exception {
        return Optional.empty();
    }
}
