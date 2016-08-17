package com.liferay.mobile.screens.base;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreEvent {

	public MediaStoreEvent(String filePath) {
		_filePath = filePath;
	}

	public String getFilePath() {
		return _filePath;
	}

	private String _filePath;
}
