package com.liferay.mobile.screens.base.list.interactor;

import android.util.Pair;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import org.json.JSONObject;

public class Query {

	private int startRow;
	private int endRow;
	private String objC;

	public int getStartRow() {
		return startRow;
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

	public JSONObjectWrapper getObjC() {
		//FIXME
		JSONObjectWrapper jsonObjectWrapper = new JSONObjectWrapper(new JSONObject());
		return jsonObjectWrapper;
	}

	public Pair<Integer, Integer> getRowRange() {
		return new Pair<>(startRow, endRow);
	}

	public void setObjC(String objC) {
		this.objC = objC;
	}

	public Query(int startRow, int endRow, String objC) {
		this.startRow = startRow;
		this.endRow = endRow;
		this.objC = objC;
	}
}