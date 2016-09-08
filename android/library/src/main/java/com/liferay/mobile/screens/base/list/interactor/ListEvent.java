package com.liferay.mobile.screens.base.list.interactor;

import com.liferay.mobile.screens.base.thread.event.CachedEvent;
import org.json.JSONObject;

public abstract class ListEvent<E> extends CachedEvent {

	public ListEvent() {
		super();
	}

	public ListEvent(JSONObject jsonObject) {
		super(jsonObject);
	}

	public ListEvent(Exception e) {
		super(e);
	}

	public abstract String getListKey();

	public abstract E getModel();
}
