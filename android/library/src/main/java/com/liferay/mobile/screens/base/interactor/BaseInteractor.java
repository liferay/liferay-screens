package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.base.interactor.event.ErrorEvent;
import com.liferay.mobile.screens.cache.executor.Executor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class BaseInteractor<L, E extends BasicEvent> implements Interactor<L> {

	protected L listener;
	private int targetScreenletId;
	private String actionName;

	public BaseInteractor() {
		super();
	}

	public void start(final Object... args) {
		Executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					E event = execute(args);
					if (event != null) {
						decorateBaseEvent(event);
						EventBusUtil.post(event);
					}
				} catch (Exception e) {
					ErrorEvent errorNewEvent = new ErrorEvent(e);
					decorateBaseEvent(errorNewEvent);
					EventBusUtil.post(errorNewEvent);
				}
			}
		});
	}

	public void onEventMainThread(ErrorEvent event) {

		if (getListener() == null || event.getTargetScreenletId() != getTargetScreenletId()) {
			return;
		}

		onFailure(event.getException());
	}

	public void processEvent(E event) {
		try {
			LiferayLogger.i("event = [" + event + "]");
			if (isInvalidEvent(event)) {
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

	protected boolean isInvalidEvent(BasicEvent event) {
		return getListener() == null || event.getTargetScreenletId() != getTargetScreenletId() || !actionName.equals(
			event.getActionName());
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

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	protected void decorateBaseEvent(BasicEvent event) {
		event.setTargetScreenletId(getTargetScreenletId());
		event.setActionName(getActionName());
	}
}
