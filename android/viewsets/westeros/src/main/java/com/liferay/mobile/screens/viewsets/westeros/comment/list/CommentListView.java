package com.liferay.mobile.screens.viewsets.westeros.comment.list;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Alejandro Hernández
 */
public class CommentListView extends com.liferay.mobile.screens.viewsets.defaultviews.comment.list.CommentListView {

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.comment_row_westeros;
    }
}
