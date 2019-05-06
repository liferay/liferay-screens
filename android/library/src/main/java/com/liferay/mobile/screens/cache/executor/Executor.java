package com.liferay.mobile.screens.cache.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Javier Gamarra
 */
public class Executor {

    public static final int N_THREADS = 3;
    private static ExecutorService executor;

    private Executor() {
        super();
    }

    public static synchronized ExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(N_THREADS);
        }
        return executor;
    }

    public static void execute(Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("Runnable to execute cannot be null");
        }
        getExecutor().execute(runnable);
    }
}
