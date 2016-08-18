package com.liferay.mobile.screens.base.thread;

import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.base.thread.event.ErrorThreadEvent;
import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import com.liferay.mobile.screens.base.thread.listener.OfflineListenerNew;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedThreadRemoteInteractor<L extends OfflineListenerNew, E extends OfflineEventNew>
	extends BaseThreadInteractor<L, E> {

	protected long groupId;
	protected long userId;
	protected Locale locale;
	protected OfflinePolicy _offlinePolicy;

	public void start(final Object... args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (_offlinePolicy == OfflinePolicy.CACHE_FIRST) {
						try {
							boolean _retrievedFromCache = cached(args);

							if (!_retrievedFromCache) {
								online(true, null, args);
							}
						} catch (Exception e) {
							online(true, e, args);
						}
					} else if (_offlinePolicy == OfflinePolicy.CACHE_ONLY) {
						LiferayLogger.i("Trying to retrieve object from cache");

						boolean _retrievedFromCache = cached(args);

						if (!_retrievedFromCache) {
							throw new NoSuchElementException();
						}
					} else if (_offlinePolicy == OfflinePolicy.REMOTE_FIRST) {
						try {
							online(false, null, args);
						} catch (Exception e) {
							LiferayLogger.e("Retrieve online first failed, trying cached version", e);

							boolean _retrievedFromCache = cached(args);

							if (!_retrievedFromCache) {
								throw new NoSuchElementException();
							}
						}
					} else {
						online(false, null, args);
					}
				} catch (Exception e) {
					BasicThreadEvent event = new ErrorThreadEvent(e);
					event.setTargetScreenletId(getTargetScreenletId());
					EventBusUtil.post(event);
				}
			}
		}).start();
	}

	public void onEventMainThread(E event) {
		try {
			LiferayLogger.i("event = [" + event + "]");

			if (!isValidEvent(event)) {
				return;
			}

			if (event.isFailed()) {
				onFailure(event.getException());
			} else {

				if (!event.isCachedRequest()) {
					storeToCache(event);
				}

				onSuccess(event);
			}
		} catch (Exception e) {
			onFailure(e);
		}
	}

	@Override
	public void onFailure(Exception e) {
		getListener().error(e, null);
	}

	protected void online(boolean triedOffline, Exception e, Object[] args) throws Exception {

		if (triedOffline) {
			LiferayLogger.i("Retrieve from cache first failed, trying online");
		}

		getListener().retrievingOnline(triedOffline, e);

		E newEvent = execute(args);
		newEvent.setTargetScreenletId(getTargetScreenletId());
		newEvent.setCachedRequest(false);
		newEvent.setGroupId(groupId);
		newEvent.setLocale(locale);
		newEvent.setUserId(userId);
		newEvent.setCacheKey(getIdFromArgs(args));
		EventBusUtil.post(newEvent);
	}

	protected boolean cached(Object... args) throws Exception {

		String cacheKey = getIdFromArgs(args);

		Class clasz = getEventClass();

		String id = clasz.getName() + "_" + groupId + "_" + userId + "_" + locale + "_" + cacheKey;

		return cachedById(id, clasz);
	}

	private Class getEventClass() {

		Class clasz = (Class) getClass();
		Type genericSuperclass = clasz.getGenericSuperclass();
		while (!(genericSuperclass instanceof ParameterizedType)) {
			clasz = clasz.getSuperclass();
			genericSuperclass = clasz.getGenericSuperclass();
		}

		return (Class) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
	}

	protected String getFullId(E event) throws Exception {
		return event.getClass().getName()
			+ "_"
			+ event.getGroupId()
			+ "_"
			+ event.getUserId()
			+ "_"
			+ event.getLocale()
			+ "_"
			+ event.getCacheKey();
	}

	protected void storeToCache(E event) throws Exception {

		getListener().storingToCache(event);

		DB snappydb = DBFactory.open(LiferayScreensContext.getContext());
		snappydb.put(getFullId(event), event);
		snappydb.close();
	}

	protected boolean cachedById(String fullId, Class<E> clazz) throws SnappydbException {
		DB snappyDB = DBFactory.open(LiferayScreensContext.getContext());
		E offlineEvent = snappyDB.getObject(fullId, clazz);
		if (offlineEvent != null) {
			offlineEvent.setCachedRequest(true);
			offlineEvent.setTargetScreenletId(getTargetScreenletId());
			EventBusUtil.post(offlineEvent);
			getListener().loadingFromCache(true);
			return true;
		}
		getListener().loadingFromCache(false);
		return false;
	}

	protected abstract String getIdFromArgs(Object... args) throws Exception;

	protected OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		_offlinePolicy = offlinePolicy;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
