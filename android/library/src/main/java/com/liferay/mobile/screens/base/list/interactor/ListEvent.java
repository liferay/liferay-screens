package com.liferay.mobile.screens.base.list.interactor;

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import org.json.JSONObject;

public abstract class ListEvent<E> extends OfflineEventNew {

	public ListEvent() {
		super();
	}

	public ListEvent(JSONObject jsonObject) {
		super(jsonObject);
	}

	public ListEvent(Exception e) {
		super(e);
	}

	public abstract String getCacheKey();

	public abstract E getModel();
}
