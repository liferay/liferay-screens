package com.liferay.mobile.screens.base.list.interactor;

import android.util.Pair;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.base.thread.event.ErrorThreadEvent;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public abstract class BaseListInteractor<E, L extends BaseListInteractorListener>
	extends BaseCachedThreadRemoteInteractor<L, BaseListEvent<E>> {

	private Query query;

	public BaseListEvent<E> execute(Object... args) throws Exception {
		throw new AssertionError("a");
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
								online(true, null, query, args);
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

	public BaseListEvent execute(Query query, Object... args) throws Exception {

		int startRow = query.getStartRow();
		int endRow = query.getEndRow();

		validate(startRow, endRow, locale);

		Pair<Integer, Integer> rowsRange = new Pair<>(startRow, endRow);

		RequestState requestState = RequestState.getInstance();

		// check if this page is already being loaded
		if (requestState.contains(getTargetScreenletId(), rowsRange)) {
			throw new AssertionError("Page already requested");
		}

		JSONArray jsonArray = getPageRowsRequest(query, args);
		int rowCount = getPageRowCountRequest(args);

		List<E> entries = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			entries.add(createEntity(JSONUtil.toMap(jsonObject)));
		}

		requestState.put(getTargetScreenletId(), rowsRange);

		return new BaseListEvent(startRow, endRow, entries, rowCount);
	}

	@Override
	public void onSuccess(BaseListEvent event) throws Exception {

		cleanRequestState(event.getStartRow(), event.getEndRow());

		List entries = event.getEntries();
		int rowCount = event.getRowCount();
		getListener().onListRowsReceived(event.getStartRow(), event.getEndRow(), entries, rowCount);
	}

	@Override
	public void onFailure(Exception e) {
		cleanRequestState(0, 0);
		getListener().onListRowsFailure(0, 0, e);
	}

	private void onFailure(BaseListEvent event) {
		cleanRequestState(event.getStartRow(), event.getEndRow());
		getListener().onListRowsFailure(event.getStartRow(), event.getEndRow(), event.getException());
	}

	private void cleanRequestState(int startRow, int endRow) {
		Pair<Integer, Integer> rowsRange = new Pair<>(startRow, endRow);
		RequestState.getInstance().remove(getTargetScreenletId(), rowsRange);
	}

	//	return String.format(Locale.US, "%s_%05d", recordSetId, row);

	protected void validate(int startRow, int endRow, Locale locale) {
		if (startRow < 0) {
			throw new IllegalArgumentException("Start row cannot be negative");
		} else if (endRow < 0) {
			throw new IllegalArgumentException("End row cannot be negative");
		} else if (startRow >= endRow) {
			throw new IllegalArgumentException("Start row cannot be greater or equals than end row");
		} else if (locale == null) {
			throw new IllegalArgumentException("Locale cannot be empty");
		}
	}

	protected abstract JSONArray getPageRowsRequest(Query query, Object... args) throws Exception;

	protected abstract Integer getPageRowCountRequest(Object... args) throws Exception;

	protected abstract E createEntity(Map<String, Object> stringObjectMap);

	public void onEventMainThread(BaseListEvent event) {
		try {
			LiferayLogger.i("event = [" + event + "]");

			if (!isValidEvent(event)) {
				return;
			}

			if (event.isFailed()) {
				onFailure(event);
			} else {
				if (!event.isCachedRequest()) {
					storeToCache(event);
				}

				onSuccess(event);
			}
		} catch (Exception e) {
			onFailure(event);
		}
	}

	@Override
	protected boolean cachedById(String fullId, Class<BaseListEvent<E>> clazz) throws SnappydbException {
		DB snappyDB = DBFactory.open(LiferayScreensContext.getContext());
		BaseListEvent offlineEvent = snappyDB.getObject(fullId, clazz);
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

	@Override
	protected void storeToCache(BaseListEvent<E> event) throws Exception {

		getListener().storingToCache(event);

		DB snappydb = DBFactory.open(LiferayScreensContext.getContext());
		snappydb.put(getFullId(event), event);
		snappydb.close();
	}

	protected String getFullId(BaseListEvent<E> event) throws Exception {
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

	protected void online(boolean triedOffline, Exception e, Query query, Object[] args) throws Exception {

		if (triedOffline) {
			LiferayLogger.i("Retrieve from cache first failed, trying online");
		}

		getListener().retrievingOnline(triedOffline, e);

		BaseListEvent<E> newEvent = execute(query, args);
		//FIXME !
		//decorateEvent(newEvent);
		EventBusUtil.post(newEvent);
	}

	protected long groupId;
	protected long userId;
	protected Locale locale;
	protected OfflinePolicy _offlinePolicy;

	public void setQuery(Query query) {
		this.query = query;
	}
}