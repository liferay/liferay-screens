package com.liferay.mobile.screens.base.list.interactor;

import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public abstract class BaseListInteractor<L extends BaseListInteractorListener, E extends ListEvent>
	extends BaseCachedThreadRemoteInteractor<L, BaseListEvent<E>> {

	protected Query query;

	public BaseListEvent<E> execute(Query query, Object... args) throws Exception {
		int startRow = query.getStartRow();
		int endRow = query.getEndRow();

		validate(startRow, endRow, locale);

		if (notRequestingRightNow(query)) {

			JSONArray jsonArray = getPageRowsRequest(query, args);
			int rowCount = getPageRowCountRequest(args);

			List<E> entries = new ArrayList<>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				entries.add(createEntity(JSONUtil.toMap(jsonObject)));
			}

			return new BaseListEvent<>(query, entries, rowCount);
		}

		return null;
	}

	@Override
	public void onSuccess(BaseListEvent event) throws Exception {

		List<E> entries = event.getEntries();
		int rowCount = event.getRowCount();

		List list = new ArrayList();

		for (E element : entries) {
			list.add(element.getModel());
		}

		cleanRequestState(event.getQuery());

		getListener().onListRowsReceived(event.getStartRow(), event.getEndRow(), list, rowCount);
	}

	@Override
	public BaseListEvent<E> execute(Object... args) throws Exception {
		throw new AssertionError("Should not be called!");
	}

	@Override
	public void onFailure(Exception e) {

		RequestState.getInstance().clear(getTargetScreenletId());

		getListener().onListRowsFailure(0, 0, e);
	}

	protected void cleanRequestState(Query query) {
		synchronized (this) {
			RequestState.getInstance().remove(getTargetScreenletId(), query.getRowRange());
		}
	}

	protected boolean notRequestingRightNow(Query query) {
		synchronized (this) {
			if (!RequestState.getInstance().contains(getTargetScreenletId(), query.getRowRange())) {
				RequestState.getInstance().put(getTargetScreenletId(), query.getRowRange());
				return true;
			}
			return false;
		}
	}

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

	protected boolean cached(Object... args) throws Exception {

		String cacheKey = getListId(query, args);
		Class clasz = BaseListEvent.class;

		String listKey = getFullId(clasz, groupId, userId, locale, cacheKey, null);

		DB snappyDB = DBFactory.open(LiferayScreensContext.getContext());
		BaseListEvent offlineEvent = (BaseListEvent) snappyDB.getObject(listKey, clasz);
		if (offlineEvent != null) {

			decorateBaseEvent(offlineEvent);
			offlineEvent.setCachedRequest(true);

			Class childClass = getEventClass();

			String elementKey = getFullId(childClass, groupId, userId, locale, null, null);

			String keys[] = snappyDB.findKeys(elementKey, offlineEvent.getQuery().getStartRow(),
				offlineEvent.getQuery().getLimit());
			snappyDB.close();

			List<E> entries = new ArrayList<>();
			for (String key : keys) {
				entries.add((E) snappyDB.getObject(key, childClass));
			}
			offlineEvent.setEntries(entries);

			EventBusUtil.post(offlineEvent);
			getListener().loadingFromCache(true);
			return true;
		}
		snappyDB.close();
		getListener().loadingFromCache(false);
		return false;
	}

	@NonNull
	private String getListId(Query query, Object... args) throws Exception {
		return getIdFromArgs(args) + "-" + query.getStartRowFormatted() + "-" + query.getEndRowFormatted();
	}

	protected void storeToCache(BaseListEvent event) throws Exception {

		getListener().storingToCache(event);

		DB snappydb = DBFactory.open(LiferayScreensContext.getContext());

		String listKey =
			getFullId(event.getClass(), event.getGroupId(), event.getUserId(), event.getLocale(), event.getCacheKey(),
				null);

		List<E> entries = event.getEntries();
		for (int i = 0; i < entries.size(); i++) {

			E entry = entries.get(i);

			//String id = getFullId(entry.getClass(), event.getGroupId(), event.getUserId(), event.getLocale(),
			//	entry.getCacheKey(), i + query.getStartRow());

			String id = getFullId(entry.getClass(), event.getGroupId(), event.getUserId(), event.getLocale(),
				entry.getCacheKey(), null);
			snappydb.put(id, entry);
		}

		event.setEntries(new ArrayList());

		snappydb.put(listKey, event);

		event.setEntries(entries);
		snappydb.close();
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	protected void online(boolean triedOffline, Exception e, Object[] args) throws Exception {

		if (triedOffline) {
			LiferayLogger.i("Retrieve from cache first failed, trying online");
		}

		getListener().retrievingOnline(triedOffline, e);

		BaseListEvent<E> newEvent = execute(query, args);
		if (newEvent != null) {
			decorateEvent(newEvent, false);

			newEvent.setCacheKey(getListId(query, args));
			EventBusUtil.post(newEvent);
		}
	}

	protected abstract JSONArray getPageRowsRequest(Query query, Object... args) throws Exception;

	protected abstract Integer getPageRowCountRequest(Object... args) throws Exception;

	protected abstract E createEntity(Map<String, Object> stringObjectMap);
}