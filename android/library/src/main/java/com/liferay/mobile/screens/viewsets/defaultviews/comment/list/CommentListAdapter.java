package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.support.annotation.NonNull;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.comment.display.CommentDisplayListener;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentListAdapter extends BaseListAdapter<CommentEntry, CommentListAdapter.CommentViewHolder> {

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

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassPK(long classPK) {
		this.classPK = classPK;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
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

		public CommentViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);
			commentDisplayScreenlet = (CommentDisplayScreenlet) view.findViewById(R.id.comment_view);
			commentDisplayScreenlet.setListener(commentDisplayListener);
			commentDisplayScreenlet.setGroupId(groupId);
			commentDisplayScreenlet.setClassName(className);
			commentDisplayScreenlet.setClassPK(classPK);
		}

		public void bind(CommentEntry entry) {
			commentDisplayScreenlet.setEditable(editable);
			this.entry = entry;
		}

		private CommentEntry entry;
		private final CommentDisplayScreenlet commentDisplayScreenlet;

		public void loadDisplayScreenlet() {
			commentDisplayScreenlet.onLoadCommentSuccess(entry);
		}
	}

	private CommentDisplayListener commentDisplayListener;

	private String className;
	private long classPK;
	private long groupId;
	private boolean editable;
}
