package com.liferay.mobile.screens.base.list.interactor;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.liferay.mobile.android.service.BatchSessionImpl;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLocale;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public abstract class BaseListInteractor<E, L extends BaseListInteractorListener>
	extends BaseCachedRemoteInteractor<L, BaseListEvent> {

	public BaseListInteractor(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	public void loadRows(
		int startRow, int endRow, Locale locale)
		throws Exception {

		validate(startRow, endRow, locale);

		Pair<Integer, Integer> rowsRange = new Pair<>(startRow, endRow);

		RequestState requestState = RequestState.getInstance();

		// check if this page is already being loaded
		if (requestState.contains(getTargetScreenletId(), rowsRange)) {
			return;
		}

		BatchSessionImpl session = getSession(rowsRange, locale);

		getPageRowsRequest(session, startRow, endRow, locale);
		getPageRowCountRequest(session);

		session.invoke();

		requestState.put(getTargetScreenletId(), rowsRange);
	}

	public void onEventMainThread(BaseListEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		onEventWithCache(event);

		if (!event.isFailed()) {
			List entries = event.getEntries();
			int rowCount = event.getRowCount();

			getListener().onListRowsReceived(
				event.getStartRow(), event.getEndRow(), entries, rowCount);
		}
	}

	@Override
	protected void online(Object[] args) throws Exception {

		final int startRow = (int) args[0];
		final int endRow = (int) args[1];
		final Locale locale = (Locale) args[2];

		loadRows(startRow, endRow, locale);
	}

	@Override
	protected void notifyError(BaseListEvent event) {
		getListener().onListRowsFailure(
			event.getStartRow(), event.getEndRow(), event.getException());
	}

	protected boolean recoverRows(String id, CachedType type, CachedType typeCount, Long groupId, Long userId,
								  Locale locale, int startRow, int endRow)
		throws JSONException {

		String query = " AND "
			+ TableCache.ID + " >= ? AND "
			+ TableCache.ID + " < ? AND "
			+ TableCache.USER_ID + " = ? AND "
			+ TableCache.GROUP_ID + " = ? AND "
			+ TableCache.LOCALE + " = ? ";

		String startId = createId(id, startRow);
		String endId = createId(id, endRow);

		Long defaultGroupId = groupId == null ? LiferayServerContext.getGroupId() : groupId;
		Long defaultUserId = userId == null ? (long) SessionContext.getDefaultUserId() : userId;
		String defaultLocale = locale == null ? LiferayLocale.getDefaultSupportedLocale() :
			LiferayLocale.getSupportedLocale(locale.getDisplayLanguage());

		Cache cache = CacheSQL.getInstance();
		List<TableCache> elements = (List<TableCache>) cache.get(type, query, startId, endId, defaultUserId, defaultGroupId, defaultLocale);

		if (elements != null && !elements.isEmpty()) {

			List<E> entries = new ArrayList<>();

			for (TableCache tableCache : elements) {
				entries.add(getElement(tableCache));
			}

			TableCache tableCache = (TableCache) cache.getById(typeCount, id, groupId, userId, locale);

			Integer rowCount = Integer.valueOf(tableCache.getContent());

			BaseListEvent event = new BaseListEvent(getTargetScreenletId(), startRow, endRow, locale, entries, rowCount);
			EventBusUtil.post(event);

			return true;
		}
		return false;
	}

	@NonNull
	protected abstract E getElement(TableCache tableCache) throws JSONException;

	protected String createId(String recordSetId, Integer row) {
		return String.format("%s_%05d", recordSetId, row);
	}

	protected void storeRows(String id, CachedType cachedType, CachedType cachedTypeCount, Long groupId, Long userId, BaseListEvent event) {
		Cache cache = CacheSQL.getInstance();

		cache.set(new TableCache(id, cachedTypeCount, String.valueOf(event.getRowCount()),
			groupId, userId, event.getLocale()));

		for (int i = 0; i < event.getEntries().size(); i++) {

			int range = i + event.getStartRow();

			String content = getContent((E) event.getEntries().get(i));

			cache.set(new TableCache(createId(id, range), cachedType, content, groupId, userId, event.getLocale()));
		}
	}

	protected abstract String getContent(E object);

	protected BatchSessionImpl getSession(Pair<Integer, Integer> rowsRange, Locale locale) {
		Session currentSession = SessionContext.createSessionFromCurrentSession();

		BatchSessionImpl batchSession = new BatchSessionImpl(currentSession);

		batchSession.setCallback(getCallback(rowsRange, locale));

		return batchSession;
	}

	protected void validate(
		int startRow, int endRow, Locale locale) {

		if (startRow < 0) {
			throw new IllegalArgumentException("Start row cannot be negative");
		}

		if (endRow < 0) {
			throw new IllegalArgumentException("End row cannot be negative");
		}

		if (startRow >= endRow) {
			throw new IllegalArgumentException("Start row cannot be greater or equals than end row");
		}

		if (locale == null) {
			throw new IllegalArgumentException("Locale cannot be empty");
		}
	}

	protected abstract BaseListCallback<E> getCallback(Pair<Integer, Integer> rowsRange, Locale locale);

	protected abstract void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception;

	protected abstract void getPageRowCountRequest(Session session) throws Exception;

}