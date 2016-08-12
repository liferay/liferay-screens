package com.liferay.mobile.screens.viewsets.defaultviews.gallery.slideshow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.gallery.view.GalleryViewModel;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import com.liferay.mobile.screens.viewsets.defaultviews.gallery.BaseGalleryView;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowGalleryView extends
	BaseGalleryView<SlideshowGalleryAdapter.SlideshowGalleryViewHolder, SlideshowGalleryAdapter> {

	public SlideshowGalleryView(Context context) {
		super(context);
	}

	public SlideshowGalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlideshowGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SlideshowGalleryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected SlideshowGalleryAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new SlideshowGalleryAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		transformViews(_recyclerView);
	}

	protected void transformViews(final RecyclerView recyclerView) {
		LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		recyclerView.setLayoutManager(layout);
		recyclerView.addOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				int childCount = recyclerView.getChildCount();
				int width = recyclerView.getChildAt(0).getWidth();
				int padding = (recyclerView.getWidth() - width) / 2;

				for (int j = 0; j < childCount; j++) {
					View v = recyclerView.getChildAt(j);

					float rate = 0;
					if (v.getLeft() <= padding) {
						if (v.getLeft() >= padding - v.getWidth()) {
							rate = (padding - v.getLeft()) * 1f / v.getWidth();
						} else {
							rate = 1;
						}
						v.setScaleY(1 - rate * 0.1f);
						v.setScaleX(1 - rate * 0.1f);
					} else {

						if (v.getLeft() <= recyclerView.getWidth() - padding) {
							rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
						}
						v.setScaleY(0.9f + rate * 0.1f);
						v.setScaleX(0.9f + rate * 0.1f);
					}
				}
			}
		});

		recyclerView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
				int oldRight, int oldBottom) {
				if (recyclerView.getChildCount() < 3) {
					if (recyclerView.getChildAt(1) != null) {
						if (((RecyclerViewPager) recyclerView).getCurrentPosition() == 0) {
							View v1 = recyclerView.getChildAt(1);
							v1.setScaleY(0.9f);
							v1.setScaleX(0.9f);
						} else {
							View v1 = recyclerView.getChildAt(0);
							v1.setScaleY(0.9f);
							v1.setScaleX(0.9f);
						}
					}
				} else {
					if (recyclerView.getChildAt(0) != null) {
						View v0 = recyclerView.getChildAt(0);
						v0.setScaleY(0.9f);
						v0.setScaleX(0.9f);
					}
					if (recyclerView.getChildAt(2) != null) {
						View v2 = recyclerView.getChildAt(2);
						v2.setScaleY(0.9f);
						v2.setScaleX(0.9f);
					}
				}
			}
		});
	}

	@Override
	protected int getItemProgressLayoutId() {
		return R.layout.list_item_progress_empty;
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.gallery_item_slideshow;
	}

	@Override
	protected DividerItemDecoration getDividerDecoration() {
		return null;
	}
}
