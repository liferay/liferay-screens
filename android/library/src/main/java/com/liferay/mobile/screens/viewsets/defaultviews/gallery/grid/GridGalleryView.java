package com.liferay.mobile.screens.viewsets.defaultviews.gallery.grid;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.BaseGalleryView;

/**
 * @author Víctor Galán Grande
 */
public class GridGalleryView extends BaseGalleryView<GridGalleryAdapter.GridGalleryViewHolder, GridGalleryAdapter>
	implements View.OnClickListener {

	private static final int DEFAULT_COLS = 3;
	public int columnsSize = DEFAULT_COLS;

	public GridGalleryView(Context context) {
		super(context);
	}

	public GridGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public GridGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void reloadView() {
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsSize));
	}

	@Override
	public void onClick(View v) {
		openMediaSelector();
	}

	@Override
	protected GridGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new GridGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.gallery_item_grid;
	}

	@Override
	protected int getItemProgressLayoutId() {
		return R.layout.list_item_progress_empty;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsSize));
		FloatingActionButton uploadFAB = (FloatingActionButton) findViewById(R.id.liferay_upload_fab);
		uploadFAB.setOnClickListener(this);
	}

	@Override
	protected DividerItemDecoration getDividerDecoration() {
		int imagesSpacing = 3;
		return new GridDividerItemDecoration(imagesSpacing);
	}
}
