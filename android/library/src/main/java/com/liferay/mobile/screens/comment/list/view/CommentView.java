package com.liferay.mobile.screens.comment.list.view;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Alejandro Hernández
 */
public class CommentView extends RelativeLayout {
	public CommentView(Context context) {
		super(context);
	}

	public CommentView(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.comment_view, this, true);
	}

	public void setCommentEntry(final CommentEntry commentEntry) {
		deletionMode(false);
		editionMode(false);

		_userPortraitScreenlet.setUserId(commentEntry.getUserId());

		_userNameTextView.setText(commentEntry.getUserName());

		_createDateTextView.setText(commentEntry.getCreateDateAsTimeSpan());

		if (commentEntry.getModifiedDate() != commentEntry.getCreateDate()) {
			_editedTextView.setVisibility(VISIBLE);
		} else {
			_editedTextView.setVisibility(GONE);
		}

		if (_htmlBody) {
			_bodyTextView.setText(Html.fromHtml(commentEntry.getBody()));
		} else {
			_bodyTextView.setText(Html.fromHtml(commentEntry.getBody()).toString().replaceAll("\n", "").trim());
		}

		int childCount = commentEntry.getChildCount();

		if (childCount > 0) {
			_childCountTextView.setText(getContext().getResources().getQuantityString(
				R.plurals.number_replys, childCount, childCount));
			_childCountTextView.setVisibility(VISIBLE);
		} else {
			_childCountTextView.setVisibility(GONE);
		}

		if (commentEntry.getUserId() == SessionContext.getUserId()) {
			_editImageButton.setVisibility(VISIBLE);
			_deleteImageButton.setVisibility(VISIBLE);

			_editBodyEditText.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					editionMode(false);
					editCommentBody(commentEntry.getCommentId());
				}
			});

			_editImageButton.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					editionMode(!_isEditing);
					editCommentBody(commentEntry.getCommentId());
				}
			});

			_deleteImageButton.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					if (_isDeleting && getListener() != null) {
						getListener().onDeleteButtonClicked(commentEntry.getCommentId());
					}
					deletionMode(!_isDeleting);
				}
			});
		} else {
			_editImageButton.setVisibility(GONE);
			_deleteImageButton.setVisibility(GONE);
		}
	}

	private void editCommentBody(long commentId) {
		if (!_isEditing) {
			String editedText = _editBodyEditText.getText().toString();
			if (getListener() != null && !editedText.equals(_bodyTextView.getText())) {
				getListener().onEditButtonClicked(commentId, editedText);
			}
		}
	}

	private void editionMode(boolean on) {
		_isEditing = on;

		_editImageButton.setImageResource(_isEditing ? R.drawable.default_comment_end_edit :
			R.drawable.default_comment_edit);

		if (_isEditing) {

			_editBodyEditText.setText(_bodyTextView.getText());

			if (_viewSwitcher.getCurrentView() != _editBodyEditText) {
				_viewSwitcher.showNext();
			}

		} else if(!_isEditing && _viewSwitcher.getCurrentView() != _bodyTextView) {
			_viewSwitcher.showPrevious();
		}
	}

	private void deletionMode(boolean on) {
		_isDeleting = on;
		changeDeleteButtonBackgroundDrawable(_isDeleting ? R.drawable.default_button_selector_red :
			R.drawable.default_button_selector);
	}

	private void changeDeleteButtonBackgroundDrawable(int drawable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			_deleteImageButton.setBackground(
				ContextCompat.getDrawable(getContext(), drawable));
		} else {
			_deleteImageButton.setBackgroundDrawable(
				ContextCompat.getDrawable(getContext(), drawable));
		}
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();

		_userNameTextView = (TextView) findViewById(R.id.comment_user_name);
		_bodyTextView = (TextView) findViewById(R.id.comment_body);
		_userPortraitScreenlet = (UserPortraitScreenlet) findViewById(R.id.comment_user_portrait);
		_createDateTextView = (TextView) findViewById(R.id.comment_create_date);
		_editedTextView = (TextView) findViewById(R.id.comment_edited);
		_childCountTextView = (TextView) findViewById(R.id.comment_child_number);
		_editImageButton = (ImageButton) findViewById(R.id.comment_edit_button);
		_deleteImageButton = (ImageButton) findViewById(R.id.comment_delete_button);
		_editBodyEditText = (EditText) findViewById(R.id.comment_edit_body);
		_viewSwitcher = (ViewSwitcher) findViewById(R.id.comment_view_switcher);
	}

	public void setHtmlBody(boolean htmlBody) {
		this._htmlBody = htmlBody;
	}

	public void reloadUserPortrait() {
		_userPortraitScreenlet.load();
	}

	public void hideRepliesCounter() {
		_childCountTextView.setVisibility(GONE);
	}

	public CommentViewListener getListener() {
		return _listener;
	}

	public void setListener(CommentViewListener listener) {
		this._listener = listener;
	}

	private TextView _userNameTextView;
	private TextView _bodyTextView;
	private UserPortraitScreenlet _userPortraitScreenlet;
	private TextView _createDateTextView;
	private TextView _editedTextView;
	private TextView _childCountTextView;
	private ImageButton _editImageButton;
	private ImageButton _deleteImageButton;
	private EditText _editBodyEditText;
	private ViewSwitcher _viewSwitcher;

	private CommentViewListener _listener;

	private boolean _htmlBody;
	private boolean _isDeleting;
	private boolean _isEditing;
}
