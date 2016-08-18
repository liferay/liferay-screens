package com.liferay.mobile.screens.base.thread;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.base.thread.event.ErrorThreadEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class BaseThreadInteractor<L, E extends BasicThreadEvent> implements Interactor<L> {

	public BaseThreadInteractor() {
		super();
	}

	public void start(final Object... args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					E event = execute(args);
					event.setTargetScreenletId(getTargetScreenletId());
					EventBusUtil.post(event);
				} catch (Exception e) {
					ErrorThreadEvent errorNewEvent = new ErrorThreadEvent(e);
					errorNewEvent.setTargetScreenletId(getTargetScreenletId());
					EventBusUtil.post(errorNewEvent);
				}
			}
		}).start();
	}

	public void onEventMainThread(ErrorThreadEvent event) {

		if (getListener() == null || event.getTargetScreenletId() != getTargetScreenletId()) {
			return;
		}

		onFailure(event.getException());
	}

	public void processEvent(E event) {
		try {
			LiferayLogger.i("event = [" + event + "]");
			if (!isValidEvent(event)) {
				return;
			}

			if (event.isFailed()) {
				onFailure(event.getException());
			} else {
				onSuccess(event);
			}
		} catch (Exception e) {
			onFailure(e);
		}
	}

	public abstract E execute(Object... args) throws Exception;

	public abstract void onSuccess(E event) throws Exception;

	public abstract void onFailure(Exception e);

	public void onScreenletAttached(L listener) {
		this.listener = listener;
		EventBusUtil.register(this);
	}

	public void onScreenletDetached(L listener) {
		EventBusUtil.unregister(this);
		this.listener = null;
	}

	public boolean isValidEvent(E event) {
		return getListener() != null && event.getTargetScreenletId() == getTargetScreenletId();
	}

	protected Session getSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	public int getTargetScreenletId() {
		return targetScreenletId;
	}

	public void setTargetScreenletId(int targetScreenletId) {
		this.targetScreenletId = targetScreenletId;
	}

	public L getListener() {
		return listener;
	}

	private int targetScreenletId;
	private L listener;
}
