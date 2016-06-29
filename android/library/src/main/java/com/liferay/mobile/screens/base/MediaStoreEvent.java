package com.liferay.mobile.screens.base;

import com.liferay.mobile.screens.base.interactor.BasicEvent;

/**
 * @author Víctor Galán Grande
 */
public class MediaStoreEvent extends BasicEvent {

	public MediaStoreEvent(int targetScreenletId, String filePath) {
		super(targetScreenletId);
		_filePath = filePath;
	}

	public MediaStoreEvent(int targetScreenletId, Exception exception) {
		super(targetScreenletId, exception);
	}

	public String getFilePath() {
		return _filePath;
	}

	private String _filePath;
}
