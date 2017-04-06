package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.android.auth.CookieSignIn;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.cache.executor.Executor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.lang.reflect.ParameterizedType;

/**
 * @author Javier Gamarra
 */
public abstract class BaseInteractor<L, E extends BasicEvent> implements Interactor<L> {

	protected L listener;
	protected boolean retried;
	private int targetScreenletId;
	private String actionName;

	public BaseInteractor() {
		super();
	}

	public void start(final Object... args) {
		Executor.execute(() -> doInBackground(args));
	}

	protected void doInBackground(Object... args) {
		try {
			E event = execute(args);
			if (event != null) {
				decorateBaseEvent(event);
				EventBusUtil.post(event);
			}
		} catch (Exception e) {
			if (!retried && isCookieSessionAndAuthenticationError(e)) {
				retried = true;
				try {
					Session session = CookieSignIn.signIn(getSession());
					SessionContext.createCookieSession(session);
				} catch (Exception ex) {
					createErrorEvent(ex);
					return;
				}
				doInBackground(args);
			} else {
				createErrorEvent(e);
			}
		}
	}

	protected void createErrorEvent(Exception e) {
		try {
			E event = (E) getEventClass().newInstance();
			decorateBaseEvent(event);
			event.setException(e);
			EventBusUtil.post(event);
		} catch (Exception e1) {
			LiferayLogger.e("Event missing no-args constructor and swallowing exception", e);
		}
	}

	public void processEvent(E event) {
		if (isInvalidEvent(event)) {
			return;
		}
		LiferayLogger.i("event = [" + event + "]");

		if (event.isFailed()) {
			onFailure(event);
		} else {
			onSuccess(event);
		}
	}

	public abstract E execute(Object... args) throws Exception;

	public abstract void onSuccess(E event);

	public abstract void onFailure(E event);

	public void onScreenletAttached(L listener) {
		this.listener = listener;
		EventBusUtil.register(this);
	}

	public void onScreenletDetached(L listener) {
		EventBusUtil.unregister(this);
		this.listener = null;
	}

	protected boolean isInvalidEvent(BasicEvent event) {
		return getListener() == null || event.getTargetScreenletId() != getTargetScreenletId() || (actionName != null
			&& !actionName.equals(event.getActionName()));
	}

	protected Class getEventClass() {

		Class aClass = (Class) getClass();
		while (!(aClass.getGenericSuperclass() instanceof ParameterizedType)) {
			aClass = aClass.getSuperclass();
		}

		return (Class) ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments()[1];
	}

	protected void decorateBaseEvent(BasicEvent event) {
		event.setTargetScreenletId(getTargetScreenletId());
		event.setActionName(getActionName());
	}

	protected boolean isCookieSessionAndAuthenticationError(Exception e) {
		return e.getMessage().contains("Response code: 403")
			&& SessionContext.isLoggedIn()
			&& getSession().getAuthentication() instanceof CookieAuthentication;
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
}
