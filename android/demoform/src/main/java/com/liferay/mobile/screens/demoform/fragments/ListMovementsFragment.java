package com.liferay.mobile.screens.demoform.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.activities.MainActivity;
import java.util.List;

public class ListMovementsFragment extends AccountsFragment implements BaseListListener<Record> {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_movements, container, false);

		return view;
	}

	@Override
	public String getName() {
		return "Movements";
	}

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<Record> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(Record record, View view) {
		((MainActivity) getActivity()).accountClicked(record);
	}

	public static ListMovementsFragment newInstance(Record record) {
		return new ListMovementsFragment();
	}
}