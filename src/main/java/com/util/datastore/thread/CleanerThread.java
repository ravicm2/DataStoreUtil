package com.util.datastore.thread;


import com.util.datastore.impl.FileOperationsImpl;

public class CleanerThread implements Runnable {

    String filePath;
    Object content;

    public CleanerThread(String filePath, Object content) {
        this.filePath = filePath;
        this.content = content;
    }


    @Override
    public void run() {
        try {
            new FileOperationsImpl().writeValueToFile(this.filePath, this.content);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
