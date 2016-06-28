package com.liferay.mobile.screens.viewsets.defaultviews.gallery.grid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;

/**
 * http://stackoverflow.com/a/29168276
 */
public class GridDividerItemDecoration extends DividerItemDecoration {

	public GridDividerItemDecoration(int spacing) {
		super(null);
		_spacing = spacing;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
		RecyclerView.State state) {

		outRect.top = _spacing;
		outRect.bottom = _spacing;
		outRect.left = _spacing;
		outRect.right = _spacing;
	}

	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

	}

	private int _spacing;
}
