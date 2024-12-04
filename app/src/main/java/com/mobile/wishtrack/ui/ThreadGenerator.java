package com.mobile.wishtrack.ui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;

@Getter
public class ThreadGenerator {
    private static boolean singleton = false;

    private final ExecutorService dbExecutor;
    private final ExecutorService networkExecutor;

    public static ThreadGenerator newInstance() {
        if (singleton) return null;
        singleton = true;
        return new ThreadGenerator();
    }

    private ThreadGenerator() {

        dbExecutor = Executors.newSingleThreadExecutor();
        networkExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void close() {
        dbExecutor.shutdown();
        networkExecutor.shutdown();
    }
}
