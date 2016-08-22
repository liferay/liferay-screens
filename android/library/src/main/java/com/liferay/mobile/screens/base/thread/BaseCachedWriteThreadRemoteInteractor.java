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
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedWriteThreadRemoteInteractor<L extends OfflineListenerNew, E extends OfflineEventNew>
	extends BaseThreadInteractor<L, E> {

	public void start(final Object... args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					E event = createEvent(args);

					if (offlinePolicy == OfflinePolicy.CACHE_ONLY) {
						storeToCacheAndLaunchEvent(event);
					} else if (offlinePolicy == OfflinePolicy.CACHE_FIRST) {
						try {
							storeToCacheAndLaunchEvent(event);
						} catch (Exception e) {
							online(event);
						}
					} else if (offlinePolicy == OfflinePolicy.REMOTE_FIRST) {
						try {
							online(event);
						} catch (Exception e) {
							storeToCacheAndLaunchEvent(event);
							LiferayLogger.i("Store online first failed, trying to store locally version");
						}
					} else {
						online(event);
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
			if (event.isFailed()) {
				store(event);
				onSuccess(event);
			} else {
				if (!event.isCachedRequest()) {
					store(event);
				}
				onSuccess(event);
			}
		} catch (Exception e) {
			onFailure(event);
		}
	}

	protected abstract void onFailure(E event);

	public void onFailure(Exception e) {

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

	protected void store(E event) throws Exception {
		DB snappydb = DBFactory.open(LiferayScreensContext.getContext());
		snappydb.put(getFullId(event), event);
		snappydb.close();
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

	protected abstract E createEvent(Object[] args) throws Exception;

	protected OfflinePolicy getOfflinePolicy() {
		return offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		this.offlinePolicy = offlinePolicy;
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

	protected Locale locale;
	protected OfflinePolicy offlinePolicy;
	protected long groupId;
	protected long userId;
}
