package com.liferay.mobile.screens.base.list;

import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.ddl.list.DDLEntry;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public abstract class BaseListInteractor<L extends BaseListRowsListener> extends BaseInteractor<L> {

    public BaseListInteractor(int targetScreenletId) {
        super(targetScreenletId);
    }

    public void onEvent(BaseListEvent event) {
        if (!isValidEvent(event)) {
            return;
        }

        if (event.isFailed()) {
            getListener().onListRowsFailure(
                    event.getStartRow(), event.getEndRow(), event.getException());
        } else {
            List<DDLEntry> entries = event.getEntries();
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

    protected int _firstPageSize = 50;
    protected int _pageSize = 25;

}
