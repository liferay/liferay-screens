package com.liferay.mobile.screens.testapp;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.defaultviews.webcontent.list.WebContentListAdapter;
import com.liferay.mobile.screens.viewsets.defaultviews.webcontent.list.WebContentListView;

public class NewsGridLayout extends WebContentListView {

	public static final int COLUMNS_SIZE = 2;

	public NewsGridLayout(Context context) {
		super(context);
	}

	public NewsGridLayout(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public NewsGridLayout(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.my_layout;
	}

	@Override
	protected WebContentListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new NewsListCustomAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		int itemLayoutId = getItemLayoutId();
		int itemProgressLayoutId = getItemProgressLayoutId();

		recyclerView = findViewById(R.id.liferay_recycler_list);
		progressBar = findViewById(R.id.liferay_progress);

		WebContentListAdapter adapter = createListAdapter(itemLayoutId, itemProgressLayoutId);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMNS_SIZE));

		DividerItemDecoration dividerItemDecoration = (DividerItemDecoration) getDividerDecoration();
		if (dividerItemDecoration != null) {
			recyclerView.addItemDecoration(getDividerDecoration());
		}
	}
}