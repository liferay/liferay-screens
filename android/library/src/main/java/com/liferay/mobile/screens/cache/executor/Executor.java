package com.liferay.mobile.screens.cache.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Javier Gamarra
 */
public class Executor {

	private Executor() {
		super();
	}

	public static final int N_THREADS = 3;

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

	private static ExecutorService executor;
}
