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
public class CommentListAdapter
	extends BaseListAdapter<CommentEntry, CommentListAdapter.CommentViewHolder> {

	public CommentListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener,
		CommentDisplayListener commentDisplayListener) {
		super(layoutId, progressLayoutId, listener);

		_commentDisplayListener = commentDisplayListener;
	}

	@NonNull @Override
	public CommentViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new CommentViewHolder(view, listener);
	}

	@Override protected void fillHolder(CommentEntry entry, CommentViewHolder holder) {
		holder.bind(entry);
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
	}

	public class CommentViewHolder extends BaseListAdapter.ViewHolder {

		public CommentViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);
			_commentDisplayScreenlet =
				(CommentDisplayScreenlet) view.findViewById(R.id.comment_view);
			_commentDisplayScreenlet.setListener(_commentDisplayListener);
			_commentDisplayScreenlet.setGroupId(_groupId);
			_commentDisplayScreenlet.setClassName(_className);
			_commentDisplayScreenlet.setClassPK(_classPK);
		}

		public void bind(CommentEntry entry) {
			_commentDisplayScreenlet.setEditable(_editable);
			_commentDisplayScreenlet.setCommentEntry(entry);
			_commentDisplayScreenlet.refreshView();
		}

		private final CommentDisplayScreenlet _commentDisplayScreenlet;
	}

	private CommentDisplayListener _commentDisplayListener;

	private String _className;
	private long _classPK;
	private long _groupId;
	private boolean _editable;
}
