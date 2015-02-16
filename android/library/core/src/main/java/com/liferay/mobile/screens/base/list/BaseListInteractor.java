package com.liferay.mobile.screens.base.list;

import android.util.Pair;

import com.liferay.mobile.android.service.BatchSessionImpl;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.interactor.AssetListCallback;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.util.SessionContext;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public abstract class BaseListInteractor<E, L extends BaseListRowsListener> extends BaseInteractor<L> {

    public BaseListInteractor(int targetScreenletId) {
        super(targetScreenletId);
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

        Session session = SessionContext.createSessionFromCurrentSession();
        BatchSessionImpl batchSession = new BatchSessionImpl(session);
        batchSession.setCallback(
                getCallback(rowsRange));

        sendPageRequests(batchSession, startRow, endRow, locale);

        batchSession.invoke();

        requestState.put(getTargetScreenletId(), rowsRange);
    }

    public void onEvent(BaseListEvent event) {
        if (!isValidEvent(event)) {
            return;
        }

        if (event.isFailed()) {
            getListener().onListRowsFailure(
                    event.getStartRow(), event.getEndRow(), event.getException());
        } else {
            List<E> entries = event.getEntries();
            int rowCount = event.getRowCount();

            getListener().onListRowsReceived(
                    event.getStartRow(), event.getEndRow(), entries, rowCount);
        }
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
            throw new IllegalArgumentException("Locale cannot be null");
        }
    }

    protected abstract BaseListCallback<E> getCallback(Pair<Integer, Integer> rowsRange);

    protected abstract void sendPageRequests(BatchSessionImpl batchSession, int startRow, int endRow, Locale locale) throws Exception;

    protected int _firstPageSize = 50;
    protected int _pageSize = 25;

}
