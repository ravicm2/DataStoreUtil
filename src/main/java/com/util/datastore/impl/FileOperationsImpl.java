package com.util.datastore.impl;

import com.util.datastore.model.Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FileOperationsImpl {
    public <T> void writeValueToFile(String filePath, T object) throws Exception {

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            throw new Exception(e);
        }

    }

    public Optional<Map<String, Data>> readValueFromFile(String filePath) throws Exception {

        Map<String, Data> map = new ConcurrentHashMap<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            map = (Map<String, Data>) objectInputStream.readObject();

        } catch (IOException e) {
            throw new Exception(e);
        }
        return Optional.ofNullable(map);
    }
}
