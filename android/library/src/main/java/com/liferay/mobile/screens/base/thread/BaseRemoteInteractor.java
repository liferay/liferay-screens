package com.liferay.mobile.screens.base.thread;

import com.liferay.mobile.screens.base.thread.event.BasicEvent;

public abstract class BaseRemoteInteractor<L, E extends BasicEvent> extends BaseInteractor<L, E> {

	public void onEventMainThread(E event) {
		super.processEvent(event);
	}
}
