package com.liferay.mobile.screens.viewsets.defaultviews.gallery.list;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.BaseGalleryView;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.grid.GridGalleryAdapter;

/**
 * @author Víctor Galán Grande
 */
public class ListGalleryView extends BaseGalleryView<ListGalleryAdapter.ListGalleryViewHolder, ListGalleryAdapter>
	implements View.OnClickListener {

	public ListGalleryView(Context context) {
		super(context);
	}

	public ListGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ListGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected ListGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new ListGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.gallery_item_list;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		FloatingActionButton _uploadFAB = (FloatingActionButton) findViewById(R.id.liferay_upload_fab);
		_uploadFAB.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		openMediaSelector();
	}
}
