package com.liferay.mobile.screens.cache.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Javier Gamarra
 */
public class Executor {

	public static final int N_THREADS = 3;

	public synchronized static ExecutorService getExecutor() {
		if (_executor == null) {
			_executor = Executors.newFixedThreadPool(N_THREADS);
		}
		return _executor;
	}

	public static void execute(Runnable runnable) {
		if (runnable == null) {
			throw new IllegalArgumentException("Runnable to execute cannot be null");
		}
		getExecutor().execute(runnable);
	}

	private static ExecutorService _executor;

}
