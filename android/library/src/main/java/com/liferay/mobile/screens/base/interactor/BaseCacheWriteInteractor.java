package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.cache.executor.Executor;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCacheWriteInteractor<L extends BaseCacheListener, E extends CacheEvent>
    extends BaseCacheReadInteractor<L, E> {

    public void start(final E event) {
        Executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cachePolicy == CachePolicy.CACHE_ONLY) {
                        storeToCacheAndLaunchEvent(event);
                    } else if (cachePolicy == CachePolicy.CACHE_FIRST) {
                        try {
                            storeToCacheAndLaunchEvent(event);
                        } catch (Exception e) {
                            online(event);
                        }
                    } else if (cachePolicy == CachePolicy.REMOTE_FIRST) {
                        try {
                            online(event);
                        } catch (Exception e) {
                            event.setException(e);
                            storeToCacheAndLaunchEvent(event);
                            LiferayLogger.i("Store online first failed, trying to store locally version");
                        }
                    } else {
                        online(event);
                    }
                } catch (Exception e) {
                    createErrorEvent(e);
                }
            }
        });
    }

    public void onEventMainThread(E event) {
        try {
            LiferayLogger.i("event = [" + event + "]");

            if (isInvalidEvent(event)) {
                return;
            }

            if (event.isFailed()) {
                event.setDirty(true);
                store(event);
                onFailure(event);
            } else {
                if (event.isOnlineRequest()) {
                    store(event);
                }
                onSuccess(event);
            }
        } catch (Exception e) {
            onFailure(event);
        }
    }

    public abstract E execute(E event) throws Exception;

    @Override
    public E execute(Object[] args) throws Exception {
        throw new AssertionError("Shouldn't be called");
    }

    protected void online(E onlineEvent) throws Exception {
        decorateEvent(onlineEvent, false);
        E event = execute(onlineEvent);
        if (event != null) {
            EventBusUtil.post(event);
        }
    }

    protected void storeToCacheAndLaunchEvent(E event) throws Exception {
        decorateEvent(event, true);
        event.setDirty(true);
        store(event);
        EventBusUtil.post(event);
    }

    protected void store(E event) throws Exception {
        Cache.storeObject(event);
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        return null;
    }
}
