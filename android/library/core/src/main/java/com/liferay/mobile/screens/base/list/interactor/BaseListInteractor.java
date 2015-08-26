package com.liferay.mobile.screens.base.list.interactor;

import android.util.Pair;

import com.liferay.mobile.android.service.BatchSessionImpl;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.BaseCachedReadRemoteInteractor;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.context.SessionContext;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public abstract class BaseListInteractor<E, L extends BaseListInteractorListener>
	extends BaseCachedReadRemoteInteractor<L, BaseListEvent> {

	public BaseListInteractor(int targetScreenletId, CachePolicy cachePolicy) {
		super(targetScreenletId, cachePolicy);
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

	public String createId(String recordSetId, Integer row) {
		return String.format("%s_%05d", recordSetId, row);
	}

	public void onEventMainThread(BaseListEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onListRowsFailure(
				event.getStartRow(), event.getEndRow(), event.getException());
		}
		else {
			List entries = event.getEntries();
			int rowCount = event.getRowCount();

			getListener().onListRowsReceived(
				event.getStartRow(), event.getEndRow(), entries, rowCount);
		}
	}

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