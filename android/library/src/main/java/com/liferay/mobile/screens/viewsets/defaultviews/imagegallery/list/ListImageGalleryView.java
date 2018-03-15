package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.list;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.BaseImageGalleryView;

/**
 * @author Víctor Galán Grande
 */
public class ListImageGalleryView
	extends BaseImageGalleryView<ListImageGalleryAdapter.ListGalleryViewHolder, ListImageGalleryAdapter>
	implements View.OnClickListener {

	public ListImageGalleryView(Context context) {
		super(context);
	}

	public ListImageGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListImageGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ListImageGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected ListImageGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new ListImageGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.gallery_item_list;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		FloatingActionButton uploadFAB = findViewById(R.id.liferay_upload_fab);
		uploadFAB.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		openMediaSelector();
	}
}
