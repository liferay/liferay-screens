package com.liferay.mobile.screens.base;

import com.liferay.mobile.screens.base.interactor.event.CachedEvent;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreEvent extends CachedEvent {

	private final String filePath;

	public MediaStoreEvent(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}
}
