package com.util.datastore.impl;

import com.util.datastore.api.DataStoreOperations;
import com.util.datastore.model.Data;
import com.util.datastore.thread.CleanerThread;
import com.util.datastore.util.StoreUtility;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStoreImpl implements DataStoreOperations {

    private static String filePath;

    private static Map<String, Data> CONCURRENT_IN_MEMORY_STORE = new ConcurrentHashMap<>();
    private static long FILE_SIZE = 0;

    public DataStoreImpl() {
        this(StoreUtility.DEFAULT_DIRECTORY_PATH);
    }

    public DataStoreImpl(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Path must be a directory");
        }

        filePath = String.format(StoreUtility.FILE_NAME, directoryPath);

        try {
            File file = new File(filePath);
            if (file.exists()) {
                updateSize();
                return;
            }
            file.createNewFile();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void updateSize() {
        try {
            updateFileSizeWhenDataPresent(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void updateFileSizeWhenDataPresent(String filePath) throws Exception {

        CONCURRENT_IN_MEMORY_STORE = new FileOperationsImpl().readValueFromFile(filePath).get();
        FILE_SIZE = StoreUtility.getJson(CONCURRENT_IN_MEMORY_STORE).getBytes().length;
    }

    @Override
    public JSONObject read(String key) throws Exception {
        StoreUtility.checkKey(key);

        Data storeValue = CONCURRENT_IN_MEMORY_STORE.get(key);

        if (storeValue != null) {
            if (storeValue.isExpired()) {
                this.delete(key);
                return null;
            }
            return storeValue.getValue();
        }
        return null;
    }

    @Override
    public boolean delete(String key) throws Exception {

        StoreUtility.checkKey(key);

        Object isRemoved = CONCURRENT_IN_MEMORY_STORE.remove(key);

        if (isRemoved != null) {
            updateFileContent();
        }
        return isRemoved != null;
    }

    @Override
    public <T> void create(T value, String key) throws Exception {
        this.create(value, key, 0);
    }

    @Override
    public <T> void create(T value, String key, long expirySeconds) throws Exception {
        if (this.read(key) != null)
            throw new Exception("Entry with given key already exists");

        if (FILE_SIZE >= StoreUtility.MAX_SIZE_FILE) {
            throw new Exception("File size exceeded the limit");
        }
        StoreUtility.checkKey(key);
        StoreUtility.checkValue(value);

        CONCURRENT_IN_MEMORY_STORE.put(key, new Data(value, expirySeconds));
        updateFileContent();

    }

    private void updateFileContent() {
        new Thread(new CleanerThread(filePath, CONCURRENT_IN_MEMORY_STORE)).start();
    }
}
