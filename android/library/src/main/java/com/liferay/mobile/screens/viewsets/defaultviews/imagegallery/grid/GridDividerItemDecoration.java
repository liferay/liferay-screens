package com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.grid;

import android.graphics.Canvas;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;

/**
 * http://stackoverflow.com/a/29168276
 */
public class GridDividerItemDecoration extends DividerItemDecoration {

    private final int spacing;

    public GridDividerItemDecoration(int spacing) {
        super(null);
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.top = spacing;
        outRect.bottom = spacing;
        outRect.left = spacing;
        outRect.right = spacing;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }
}
