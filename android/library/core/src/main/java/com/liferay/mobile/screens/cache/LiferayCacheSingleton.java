package com.liferay.mobile.screens.cache;

/**
 * @author Javier Gamarra
 */
public class LiferayCacheSingleton {

	private static LiferayCache cache;

	public static LiferayCache getInstance() {
		if (cache == null) {
			cache = new LiferayCacheSQL();
		}
		return cache;
	}

	private LiferayCacheSingleton() {
	}


}
