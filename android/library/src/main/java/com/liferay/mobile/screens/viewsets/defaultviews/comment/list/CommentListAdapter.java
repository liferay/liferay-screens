package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.support.annotation.NonNull;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.comment.CommentEntry;
import com.liferay.mobile.screens.comment.display.CommentDisplayListener;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;

/**
 * @author Alejandro Hernández
 */
public class CommentListAdapter extends BaseListAdapter<CommentEntry, CommentListAdapter.CommentViewHolder> {

    private final CommentDisplayListener commentDisplayListener;
    private boolean editable;

    public CommentListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener,
        CommentDisplayListener commentDisplayListener) {
        super(layoutId, progressLayoutId, listener);

        this.commentDisplayListener = commentDisplayListener;
    }

    @NonNull
    @Override
    public CommentViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
        return new CommentViewHolder(view, listener);
    }

    @Override
    protected void fillHolder(CommentEntry entry, CommentViewHolder holder) {
        holder.bind(entry);
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public void onViewAttachedToWindow(CommentViewHolder commentViewHolder) {
        super.onViewAttachedToWindow(commentViewHolder);

        //TODO this should be easier to do... expose a method? think in other way of doing it
        commentViewHolder.loadDisplayScreenlet();
    }

    public class CommentViewHolder extends BaseListAdapter.ViewHolder {

        private final CommentDisplayScreenlet commentDisplayScreenlet;
        private CommentEntry entry;

        public CommentViewHolder(View view, BaseListAdapterListener listener) {
            super(view, listener);
            commentDisplayScreenlet = view.findViewById(R.id.comment_view);
            commentDisplayScreenlet.setListener(commentDisplayListener);
        }

        public void bind(CommentEntry entry) {
            commentDisplayScreenlet.setEditable(editable);
            this.entry = entry;
        }

        public void loadDisplayScreenlet() {
            commentDisplayScreenlet.onLoadCommentSuccess(entry);
        }
    }
}
