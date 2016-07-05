package com.liferay.mobile.screens.base.thread;

import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.base.thread.event.ErrorThreadEvent;
import com.liferay.mobile.screens.base.thread.event.JSONThreadObjectEvent;
import com.liferay.mobile.screens.cache.CacheListener;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.NoSuchElementException;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedThreadRemoteInteractor<L extends CacheListener, E extends JSONThreadObjectEvent>
	extends BaseThreadInteractor<L, E> {

	public BaseCachedThreadRemoteInteractor(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId);

		_offlinePolicy = offlinePolicy;
	}

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
						}
						catch (Exception e) {
							online(true, e, args);
						}
					}
					else if (_offlinePolicy == OfflinePolicy.CACHE_ONLY) {
						LiferayLogger.i("Trying to retrieve object from cache");

						boolean _retrievedFromCache = cached(args);

						if (!_retrievedFromCache) {
							throw new NoSuchElementException();
						}
					}
					else if (_offlinePolicy == OfflinePolicy.REMOTE_FIRST) {
						try {
							online(false, null, args);
						}
						catch (Exception e) {
							LiferayLogger.e("Retrieve online first failed, trying cached version", e);

							boolean _retrievedFromCache = cached(args);

							if (!_retrievedFromCache) {
								throw new NoSuchElementException();
							}
						}
					}
					else {
						online(false, null, args);
					}
				}
				catch (Exception e) {
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
			}
			catch (Exception e) {
				onFailure(event.getException());
			}
		}
		else {
			try {
				if (!event.isCachedRequest()) {
					storeToCache(event);
				}

				onSuccess(event);
			}
			catch (Exception e) {
				onFailure(e);
			}
		}
	}

	protected void online(boolean triedOffline, Exception e, Object... args) throws Exception {

		if (triedOffline) {
			LiferayLogger.i("Retrieve from cache first failed, trying online");
		}

		getListener().retrievingOnline(triedOffline, e);

		E event = execute(args);
		event.setTargetScreenletId(getTargetScreenletId());
		event.setCachedRequest(false);
		EventBusUtil.post(event);
	}

	protected boolean cachedById(String fullId) throws SnappydbException {
		DB snappyDB = DBFactory.open(LiferayScreensContext.getContext());
		E offlineEvent = snappyDB.getObject(fullId, getEventClass());
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

	protected void storeToCache(E event) throws SnappydbException {

		getListener().storingToCache(event);

		DB snappydb = DBFactory.open(LiferayScreensContext.getContext());
		snappydb.put(getFullId(event), event);
		snappydb.close();
	}

	protected boolean cached(E event) throws Exception {

		String fullId = getFullId(event);
		return cachedById(fullId);
	}

	protected boolean cached(Object... args) throws Exception {

		String fullId = getFullId(getCachedContent(args));
		return cachedById(fullId);
	}

	protected String getFullId(IdCache event) {
		return getEventClass().getName() + "_" + event.getGroupId() + "_" + event.getUserId() + "_" + event.getLocale() + "_" + event.getId();
	}

	protected abstract IdCache getCachedContent(Object[] args);

	protected abstract Class<E> getEventClass();

	protected OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	private final OfflinePolicy _offlinePolicy;

}
