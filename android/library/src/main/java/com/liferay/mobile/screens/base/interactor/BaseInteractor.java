package com.liferay.mobile.screens.base.interactor;

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
                    createErrorEvent(e);
                }
            }
        });
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

        Class aClass = getClass();
        while (!(aClass.getGenericSuperclass() instanceof ParameterizedType)) {
            aClass = aClass.getSuperclass();
        }

        return (Class) ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected void decorateBaseEvent(BasicEvent event) {
        event.setTargetScreenletId(getTargetScreenletId());
        event.setActionName(getActionName());
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
