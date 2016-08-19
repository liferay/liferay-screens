package com.liferay.mobile.screens.base.list.interactor;

import android.util.Pair;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public abstract class BaseListInteractor<E, L extends BaseListInteractorListener>
	extends BaseCachedThreadRemoteInteractor<L, BaseListEvent<E>> {

	protected Query query;

	public BaseListEvent<E> execute(Object... args) throws Exception {

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

		return new BaseListEvent<>(startRow, endRow, entries, rowCount);
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

	private void cleanRequestState(int startRow, int endRow) {
		Pair<Integer, Integer> rowsRange = new Pair<>(startRow, endRow);
		RequestState.getInstance().remove(getTargetScreenletId(), rowsRange);
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

	public void setQuery(Query query) {
		this.query = query;
	}

	protected abstract JSONArray getPageRowsRequest(Query query, Object... args) throws Exception;

	protected abstract Integer getPageRowCountRequest(Object... args) throws Exception;

	protected abstract E createEntity(Map<String, Object> stringObjectMap);

	//private void onFailure(BaseListEvent event) {
	//	cleanRequestState(event.getStartRow(), event.getEndRow());
	//	getListener().onListRowsFailure(event.getStartRow(), event.getEndRow(), event.getException());
	//}

	//	return String.format(Locale.US, "%s_%05d", recordSetId, row);
}