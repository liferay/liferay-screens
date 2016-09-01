package com.liferay.mobile.screens.base.list.interactor;

import android.util.Pair;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import java.io.Serializable;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class Query implements Serializable {

	private int startRow;
	private int endRow;
	private String comparator;

	public Query() {
		super();
	}

	public Query(int startRow, int endRow, String comparator) {
		this.startRow = startRow;
		this.endRow = endRow;
		this.comparator = comparator;
	}

	public Query(Query query) {
		this.startRow = query.getStartRow();
		this.endRow = query.getEndRow();
		this.comparator = query.getComparator();
	}

	public int getStartRow() {
		return startRow;
	}

	public String getStartRowFormatted() {
		return formatted(startRow);
	}

	public String getEndRowFormatted() {
		return formatted(endRow);
	}

	private String formatted(int endRow) {
		return String.format(Locale.US, "%05d", endRow);
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public String getComparator() {
		return comparator;
	}

	public JSONObjectWrapper getComparatorJSONWrapper() throws JSONException {
		return comparator == null ? null : new JSONObjectWrapper(new JSONObject(comparator));
	}

	public void setComparator(String comparator) {
		this.comparator = comparator;
	}

	public Pair<Integer, Integer> getRowRange() {
		return new Pair<>(startRow, endRow);
	}

	public int getLimit() {
		return endRow - startRow;
	}
}