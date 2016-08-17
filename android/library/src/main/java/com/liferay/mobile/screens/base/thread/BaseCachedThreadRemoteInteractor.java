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
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedThreadRemoteInteractor<L extends OfflineListenerNew, E extends OfflineEventNew>
	extends BaseThreadInteractor<L, E> {

	private long groupId;
	private long userId;
	private Locale locale;

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
		LiferayLogger.i("event = [" + event + "]");

		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			try {
				if (OfflinePolicy.REMOTE_FIRST.equals(_offlinePolicy)) {
					boolean _retrievedFromCache = cached(event);

					if (_retrievedFromCache) {
						return;
					}
				}
				onFailure(event.getException());
			} catch (Exception e) {
				onFailure(event.getException());
			}
		} else {
			try {
				if (!event.isCachedRequest()) {
					storeToCache(event);
				}

				onSuccess(event);
			} catch (Exception e) {
				onFailure(e);
			}
		}
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
		EventBusUtil.post(newEvent);
	}

	@Override
	public void onFailure(Exception e) {
		getListener().error(e, null);
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

	protected void storeToCache(E event) throws Exception {

		getListener().storingToCache(event);

		DB snappydb = DBFactory.open(LiferayScreensContext.getContext());
		snappydb.put(getFullId(event), event);
		snappydb.close();
	}

	protected boolean cached(E event) throws Exception {

		String fullId = getFullId(event);
		return cachedById(fullId, (Class<E>) event.getClass());
	}

	protected boolean cached(Object... args) throws Exception {

		E event = createEventFromArgs(args);

		event.setTargetScreenletId(getTargetScreenletId());
		event.setCachedRequest(false);
		event.setGroupId(groupId);
		event.setLocale(locale);
		event.setUserId(userId);

		return cachedById(getFullId(event), (Class<E>) event.getClass());
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
			+ event.getId();
	}

	protected abstract E createEventFromArgs(Object... args) throws Exception;

	protected OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	private OfflinePolicy _offlinePolicy;

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
