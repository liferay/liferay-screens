package com.liferay.mobile.screens.base;

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreEvent extends OfflineEventNew {

	public MediaStoreEvent(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	private final String filePath;
}
