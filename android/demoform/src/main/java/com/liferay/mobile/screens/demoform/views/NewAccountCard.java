package com.liferay.mobile.screens.demoform.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.activities.FormActivity;
import java.util.List;

public class NewAccountCard extends Card implements BaseListListener<Record> {
	public NewAccountCard(Context context) {
		super(context);
	}

	public NewAccountCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NewAccountCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NewAccountCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		DDLListScreenlet ddlListScreenlet = (DDLListScreenlet) findViewById(R.id.type_of_account);
		ddlListScreenlet.setListener(this);
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
		getContext().startActivity(intent);
	}
}
