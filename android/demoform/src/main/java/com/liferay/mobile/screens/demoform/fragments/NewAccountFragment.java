package com.liferay.mobile.screens.demoform.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.activities.FormActivity;
import java.util.List;

public class NewAccountFragment extends Fragment implements BaseListListener<Record> {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.new_account, container, false);

		DDLListScreenlet ddlListScreenlet = (DDLListScreenlet) view.findViewById(R.id.type_of_account);
		ddlListScreenlet.setUserId(0);
		ddlListScreenlet.setListener(this);

		return view;
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
		Intent intent = new Intent(getContext(), FormActivity.class);
		intent.putExtra("record", record);
		//getContext().startActivity(intent);
	}
}