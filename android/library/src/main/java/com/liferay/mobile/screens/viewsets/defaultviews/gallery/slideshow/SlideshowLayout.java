package com.liferay.mobile.screens.viewsets.defaultviews.gallery.slideshow;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import com.liferay.mobile.screens.util.LiferayLogger;
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

				v.setScaleY(1 - rate * 0.3f);
				v.setScaleX(1 - rate * 0.3f);

				v.setTranslationY(rate * 200);

				v.setAlpha((1 - rate) * 1 + 0.5f);
			} else {

				if (v.getLeft() <= recyclerView.getWidth() - padding) {
					rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
				}
				v.setScaleY(0.7f + rate * 0.3f);
				v.setScaleX(0.7f + rate * 0.3f);

				v.setTranslationY((1 - rate) * 200);

				v.setAlpha(rate * 1 + 0.5f);
			}
		}
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
		int oldRight, int oldBottom) {
		

		if (recyclerView.getChildCount() < 3) {
			if (recyclerView.getChildAt(1) != null) {
				if (((RecyclerViewPager) recyclerView).getCurrentPosition() == 0) {
					View v1 = recyclerView.getChildAt(1);
					v1.setScaleY(0.7f);
					v1.setScaleX(0.7f);
					v1.setTranslationY(200);
					v1.setAlpha(0.5f);

				} else {
					View v1 = recyclerView.getChildAt(0);
					v1.setScaleY(0.7f);
					v1.setScaleX(0.7f);
					v1.setTranslationY(200);
					v1.setAlpha(0.5f);
				}
			}
		} else {
			if (recyclerView.getChildAt(0) != null) {
				View v0 = recyclerView.getChildAt(0);
				v0.setScaleY(0.7f);
				v0.setScaleX(0.7f);
				v0.setTranslationY(200);
				v0.setAlpha(0.5f);
			}
			if (recyclerView.getChildAt(2) != null) {
				View v2 = recyclerView.getChildAt(2);
				v2.setScaleY(0.7f);
				v2.setScaleX(0.7f);
				v2.setTranslationY(200);
				v2.setAlpha(0.5f);
			}

		}
	}

	private RecyclerView recyclerView;
}
