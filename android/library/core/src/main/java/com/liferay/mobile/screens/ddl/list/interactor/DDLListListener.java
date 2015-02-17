package com.liferay.mobile.screens.ddl.list.interactor;

import com.liferay.mobile.screens.ddl.list.DDLEntry;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface DDLListListener {

    public void onListPageFailed(int page, Exception e);

    public void onListPageReceived(
            int page, List<DDLEntry> entries, List<String> labelFields, int rowCount);
}
