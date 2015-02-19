package com.liferay.mobile.screens.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Javier Gamarra
 */
public class ViewUtil {

	public static int _generateUniqueId() {

		// This implementation is copied from View.generateViewId() method We
		// cannot rely on that method because it's introduced in API Level 17

		while (true) {
			final int result = sNextId.get();
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF) newValue = 1;
			if (sNextId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}

	private static final AtomicInteger sNextId = new AtomicInteger(1);
}
