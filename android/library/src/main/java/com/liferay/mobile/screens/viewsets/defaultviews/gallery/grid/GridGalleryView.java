package com.liferay.mobile.screens.viewsets.defaultviews.gallery.grid;

import android.app.AlertDialog;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.MediaStoreSelectorDialog;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.gallery.GalleryScreenlet;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.BaseGalleryView;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.UploadProgressView;
import java.util.List;
import rx.functions.Action1;

/**
 * @author Víctor Galán Grande
 */
public class GridGalleryView
	extends BaseGalleryView<GridGalleryAdapter.GridGalleryViewHolder, GridGalleryAdapter>
	implements View.OnClickListener {

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
		_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsSize));
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
	public void showStartOperation(String actionName) {
		if(actionName == null){
			super.showStartOperation(actionName);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsSize));
		FloatingActionButton _uploadFAB = (FloatingActionButton) findViewById(R.id.liferay_upload_fab);
		_uploadFAB.setOnClickListener(this);
	}

	@Override
	protected DividerItemDecoration getDividerDecoration() {
		int _imagesSpacing = 3;
		return new GridDividerItemDecoration(_imagesSpacing);
	}

	private static final int DEFAULT_COLS = 4;

	public int columnsSize = DEFAULT_COLS;
}
