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

		_retrievedFromCache = false;
		_offlinePolicy = offlinePolicy;
	}

	public void start(final Object... args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (_offlinePolicy == OfflinePolicy.CACHE_FIRST) {
						try {
							_retrievedFromCache = cached(args);

							getListener().loadingFromCache(_retrievedFromCache);

							if (!_retrievedFromCache) {
								LiferayLogger.i("Retrieve from cache first failed, trying online");

								getListener().retrievingOnline(true, null);
								online(args);
							}
						}
						catch (Exception e) {
							LiferayLogger.e("Retrieve from cache first failed, trying online", e);

							getListener().retrievingOnline(true, e);
							online(args);
						}
					}
					else if (_offlinePolicy == OfflinePolicy.CACHE_ONLY) {
						LiferayLogger.i("Trying to retrieve object from cache");

						_retrievedFromCache = cached(args);

						getListener().loadingFromCache(_retrievedFromCache);

						if (!_retrievedFromCache) {
							throw new NoSuchElementException();
						}
					}
					else if (_offlinePolicy == OfflinePolicy.REMOTE_FIRST) {
						try {

							getListener().retrievingOnline(false, null);

							online(args);
						}
						catch (Exception e) {
							LiferayLogger.e("Retrieve online first failed, trying cached version", e);

							_retrievedFromCache = cached(args);

							getListener().loadingFromCache(_retrievedFromCache);

							if (!_retrievedFromCache) {
								throw new NoSuchElementException();
							}
						}
					}
					else {

						getListener().retrievingOnline(false, null);

						online(args);
					}
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
			if (OfflinePolicy.REMOTE_FIRST.equals(_offlinePolicy)) {
				try {
					_retrievedFromCache = cached(event);

					getListener().loadingFromCache(_retrievedFromCache);

					if (!_retrievedFromCache) {
						onFailure(event);
					}
				}
				catch (Exception e) {
					onFailure(event);
				}
			}
			else {
				onFailure(event);
			}
		}
		else {
			if (hasToStoreToCache()) {
				getListener().storingToCache(event);
				storeToCache(event);
			}
			try {
				onSuccess(event);
			}
			catch (Exception e) {
				event.setException(e);
				onFailure(event);
			}
		}
	}

	protected void online(Object... args) throws Exception {
		E event = execute(args);
		event.setTargetScreenletId(getTargetScreenletId());
		EventBusUtil.post(event);
	}

	protected void storeToCache(E event) {
		try {
			DB snappydb = DBFactory.open(LiferayScreensContext.getContext());
			snappydb.put(getFullId(event), event);
			snappydb.close();
		}
		catch (SnappydbException e) {
			e.printStackTrace();
		}
	}

	protected boolean cached(E event) throws Exception {
		try {
			DB snappyDB = DBFactory.open(LiferayScreensContext.getContext());
			E myObject = snappyDB.getObject(getFullId(event), getEventClass());
			onEventMainThread(myObject);
			return true;
		}
		catch (SnappydbException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected boolean cached(Object... args) throws Exception {
		try {
			DB snappyDB = DBFactory.open(LiferayScreensContext.getContext());
			E myObject = snappyDB.getObject(getFullId(getCachedContent(args)), getEventClass());
			onEventMainThread(myObject);
			return true;
		}
		catch (SnappydbException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected abstract IdCache getCachedContent(Object[] args);

	protected abstract Class<E> getEventClass();

	protected OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	protected boolean hasToStoreToCache() {
		return !_retrievedFromCache;
	}

	private String getFullId(IdCache event) {
		return getEventClass().getName() + "_" + event.getGroupId() + "_" + event.getUserId() + "_" + event.getLocale() + "_" + event.getId();
	}

	private boolean _retrievedFromCache;
	private final OfflinePolicy _offlinePolicy;

}
