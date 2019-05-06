package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.CommentDisplayListener;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.comment.list.view.CommentListViewModel;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class CommentListView
    extends BaseListScreenletView<CommentEntry, CommentListAdapter.CommentViewHolder, CommentListAdapter>
    implements CommentListViewModel, CommentDisplayListener {

    private TextView emptyListTextView;

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
    public void allowEdition(boolean editable) {
        getAdapter().setEditable(editable);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void addNewCommentEntry(CommentEntry commentEntry) {
        getAdapter().getEntries().add(commentEntry);
        int size = getAdapter().getEntries().size();
        getAdapter().setRowCount(size);
        if (size <= 1) {
            getAdapter().notifyDataSetChanged();
        } else {
            getAdapter().notifyItemInserted(size - 1);
        }

        recyclerView.smoothScrollToPosition(size - 1);
        showEmptyListMessage();
    }

    @Override
    public void removeCommentEntry(CommentEntry commentEntry) {
        int position = getAdapter().getEntries().indexOf(commentEntry);
        getAdapter().getEntries().remove(commentEntry);
        getAdapter().setRowCount(getAdapter().getEntries().size());
        getAdapter().notifyItemRemoved(position);
        showEmptyListMessage();
    }

    private void showEmptyListMessage() {
        emptyListTextView.setVisibility(getAdapter().getEntries().isEmpty() ? VISIBLE : GONE);
    }

    @Override
    public void showFinishOperation(int startRow, int endRow, List<CommentEntry> serverEntries, int rowCount) {
        super.showFinishOperation(startRow, endRow, serverEntries, rowCount);

        showEmptyListMessage();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        emptyListTextView = findViewById(R.id.comment_empty_list);

        setFocusableInTouchMode(true);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.comment_row_default;
    }

    @Override
    protected CommentListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new CommentListAdapter(itemLayoutId, itemProgressLayoutId, this, this);
    }

    private CommentListScreenlet getCommentListScreenlet() {
        return (CommentListScreenlet) getScreenlet();
    }

    @Override
    public void onDeleteCommentSuccess(CommentEntry commentEntry) {
        getCommentListScreenlet().onDeleteCommentSuccess(commentEntry);
    }

    @Override
    public void onUpdateCommentSuccess(CommentEntry commentEntry) {
        getCommentListScreenlet().onUpdateCommentSuccess(commentEntry);
    }

    @Override
    public void onLoadCommentSuccess(CommentEntry commentEntry) {
    }

    @Override
    public void error(Exception e, String userAction) {
        getCommentListScreenlet().error(e, userAction);
    }
}
