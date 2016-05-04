package com.liferay.mobile.screens.base.thread;

import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.base.thread.event.ErrorThreadEvent;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class BaseThreadInteractor<L, E extends BasicThreadEvent>
	extends BaseRemoteInteractor<L> {

	public BaseThreadInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void start(final Object... args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					E event = execute(args);
					event.setTargetScreenletId(getTargetScreenletId());
					EventBusUtil.post(event);
				}
				catch (Exception e) {
					BasicThreadEvent basicThreadEvent = new ErrorThreadEvent(e);
					basicThreadEvent.setTargetScreenletId(getTargetScreenletId());
					EventBusUtil.post(basicThreadEvent);
				}
			}
		}).start();
	}

	public void onEventMainThread(E event) {
		LiferayLogger.i("event = [" + event + "]");
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			onFailure(event.getException());
		}
		else {
			try {
				onSuccess(event);
			}
			catch (Exception e) {
				onFailure(e);
			}
		}
	}

	public abstract E execute(Object[] args) throws Exception;

	public abstract void onFailure(Exception e);

	public abstract void onSuccess(E event) throws Exception;

	protected boolean isValidEvent(BasicThreadEvent event) {
		return getListener() != null && event.getTargetScreenletId() == getTargetScreenletId();
	}
}
