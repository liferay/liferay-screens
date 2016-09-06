package com.liferay.mobile.screens.base.thread;

import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;

public abstract class BaseRemoteInteractorNew<L, E extends BasicThreadEvent>
	extends BaseThreadInteractor<L, E> {

	public void onEventMainThread(E event) {
		super.processEvent(event);
	}
}
