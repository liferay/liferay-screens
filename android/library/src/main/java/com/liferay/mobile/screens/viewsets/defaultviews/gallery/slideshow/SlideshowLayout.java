package com.liferay.mobile.screens.viewsets.defaultviews.gallery.slideshow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * @author Víctor Galán Grande
 */
public class SlideshowLayout extends RecyclerView.OnScrollListener implements View.OnLayoutChangeListener {

	public SlideshowLayout(RecyclerView recyclerView) {
		this.recyclerView = recyclerView;
	}

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

				v.setScaleY(1 - rate * scaleDelta);
				v.setScaleX(1 - rate * scaleDelta);

				v.setTranslationY(rate * yOffset);

				v.setAlpha((1 - rate) * 1 + alphaOffset);
			} else {

				if (v.getLeft() <= recyclerView.getWidth() - padding) {
					rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
				}
				v.setScaleY(scaleOffset + rate * scaleDelta);
				v.setScaleX(scaleOffset + rate * scaleDelta);

				v.setTranslationY((1 - rate) * yOffset);

				v.setAlpha(rate * 1 + alphaOffset);
			}
		}
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
		int oldRight, int oldBottom) {

		// Set the default values to the neighbors
		if (recyclerView.getChildCount() < 3) {
			if (recyclerView.getChildAt(1) != null) {
				if (((RecyclerViewPager) recyclerView).getCurrentPosition() == 0) {
					View view = recyclerView.getChildAt(1);
					assignDefaultValues(view);
				} else {
					View view = recyclerView.getChildAt(0);
					assignDefaultValues(view);
				}
			}
		} else {
			if (recyclerView.getChildAt(0) != null) {
				View view = recyclerView.getChildAt(0);
				assignDefaultValues(view);
			}
			if (recyclerView.getChildAt(2) != null) {
				View view = recyclerView.getChildAt(2);
				assignDefaultValues(view);
			}
		}
	}

	private void assignDefaultValues(View view) {
		view.setScaleY(scaleOffset);
		view.setScaleX(scaleOffset);
		view.setTranslationY(yOffset);
		view.setAlpha(alphaOffset);
	}

	private static final int yOffset = 200;
	private static final float scaleOffset = 0.7f;
	private static final float alphaOffset = 0.5f;

	private static final float scaleDelta = 1 - scaleOffset;

	private RecyclerView recyclerView;
}
