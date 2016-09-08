package com.liferay.mobile.screens.base;

import com.liferay.mobile.screens.base.interactor.event.CacheEvent;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreEvent extends CacheEvent {

	private final String filePath;

	public MediaStoreEvent(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}
}
