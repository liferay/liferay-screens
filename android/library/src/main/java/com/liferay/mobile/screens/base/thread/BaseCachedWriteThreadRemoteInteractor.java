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

import org.json.JSONException;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedWriteThreadRemoteInteractor<L extends CacheListener, E extends JSONThreadObjectEvent>
	extends BaseThreadInteractor<L, E> {

	public BaseCachedWriteThreadRemoteInteractor(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId);

		_offlinePolicy = offlinePolicy;
	}

	public void start(final Object... args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					E event = createEvent(args);

					if (_offlinePolicy == OfflinePolicy.CACHE_ONLY) {
						storeToCacheAndLaunchEvent(event);
					}
					else if (_offlinePolicy == OfflinePolicy.CACHE_FIRST) {
						try {
							storeToCacheAndLaunchEvent(event);
						}
						catch (Exception e) {
							online(event);
						}
					}
					else if (_offlinePolicy == OfflinePolicy.REMOTE_FIRST) {
						try {
							online(event);
						}
						catch (Exception e) {
							storeToCacheAndLaunchEvent(event);
							LiferayLogger.i("Store online first failed, trying to store locally version");
						}
					}
					else {
						online(event);
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
		super.onEventMainThread(event);
		try {
			if (event.isFailed()) {
				store(event);
				onSuccess(event);
			}
			else {
				if (!event.isCachedRequest()) {
					store(event);
				}
				onSuccess(event);
			}
		}
		catch (Exception e) {
			onFailure(e);
		}
	}

	public abstract E execute(E event) throws Exception;

	@Override
	public E execute(Object[] args) throws Exception {
		throw new AssertionError("Shouldn't be called");
	}

	protected void online(E event) throws Exception {
		E resultEvent = execute(event);
		resultEvent.setTargetScreenletId(getTargetScreenletId());
		EventBusUtil.post(resultEvent);
	}

	protected void storeToCacheAndLaunchEvent(E event) throws Exception {
		store(event);
		event.setCachedRequest(true);
		event.setTargetScreenletId(getTargetScreenletId());
		EventBusUtil.post(event);
	}

	protected void store(E event) throws SnappydbException {
		DB snappydb = DBFactory.open(LiferayScreensContext.getContext());
		snappydb.put(getFullId(event), event);
		snappydb.close();
	}

	protected String getFullId(IdCache event) {
		return getEventClass().getName() + "_" + event.getGroupId() + "_" + event.getUserId() + "_" + event.getLocale() + "_" + event.getId();
	}

	protected abstract E createEvent(Object[] args) throws JSONException, Exception;

	protected abstract Class getEventClass();

	protected OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	private final OfflinePolicy _offlinePolicy;

}
