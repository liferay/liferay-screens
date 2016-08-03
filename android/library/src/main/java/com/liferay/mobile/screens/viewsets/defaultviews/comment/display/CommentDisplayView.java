package com.liferay.mobile.screens.viewsets.defaultviews.comment.display;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Alejandro Hernández
 */
public class CommentDisplayView extends FrameLayout implements CommentDisplayViewModel
{
	public CommentDisplayView(Context context) {
		super(context);
	}

	public CommentDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override public void showFinishOperation(final CommentEntry commentEntry) {
		_progressBar.setVisibility(View.GONE);
		_contentGroup.setVisibility(View.VISIBLE);

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

		_bodyTextView.setText(
			Html.fromHtml(commentEntry.getBody()).toString().replaceAll("\n", "").trim());

		if (commentEntry.isEditable()) {
			_editImageButton.setVisibility(VISIBLE);

			_editBodyEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if (actionId == EditorInfo.IME_ACTION_DONE) {
						editionMode(false);
						editCommentBody();
						return true;
					}
					return false;
				}
			});

			_editImageButton.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					editionMode(!_isEditing);
					editCommentBody();
				}
			});
		} else {
			_editImageButton.setVisibility(GONE);
		}

		if (commentEntry.isDeletable()) {
			_deleteImageButton.setVisibility(VISIBLE);

			_deleteImageButton.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					if (_isDeleting) {
						getScreenlet().performUserAction(
							CommentDisplayScreenlet.DELETE_COMMENT_ACTION);
					}
					deletionMode(!_isDeleting);
				}
			});
		} else {
			_editImageButton.setVisibility(GONE);
			_deleteImageButton.setVisibility(GONE);
		}
	}

	@Override public void showStartOperation(String actionName) {
		_progressBar.setVisibility(View.VISIBLE);
		_contentGroup.setVisibility(View.GONE);
	}

	@Override public void showFinishOperation(String actionName) {
		_progressBar.setVisibility(View.GONE);
		_contentGroup.setVisibility(View.VISIBLE);
	}

	@Override public void showFailedOperation(String actionName, Exception e) {
		_progressBar.setVisibility(View.GONE);
		_contentGroup.setVisibility(View.VISIBLE);
	}

	private void editCommentBody() {
		if (!_isEditing) {
			String editedText = _editBodyEditText.getText().toString();
			if (!editedText.equals(_bodyTextView.getText())) {
				getScreenlet().performUserAction(
					CommentDisplayScreenlet.UPDATE_COMMENT_ACTION, editedText);
			}
		}
	}

	private void editionMode(boolean on) {
		_isEditing = on;

		if (_isEditing && _viewSwitcher.getCurrentView() != _editBodyEditText) {
			_viewSwitcher.showNext();
		} else if (!_isEditing && _viewSwitcher.getCurrentView() != _bodyTextView) {
			_viewSwitcher.showPrevious();
		}

		InputMethodManager imm =
			(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (_isEditing) {
			_editImageButton.setImageResource(R.drawable.default_comment_end_edit);

			//Set selection at end of input
			_editBodyEditText.requestFocus();
			_editBodyEditText.setText("");
			_editBodyEditText.append(_bodyTextView.getText());

			imm.showSoftInput(_editBodyEditText, InputMethodManager.SHOW_FORCED);
		} else {
			_editImageButton.setImageResource(R.drawable.default_comment_edit);

			imm.hideSoftInputFromWindow(_editBodyEditText.getWindowToken(), 0);
		}
	}

	private void deletionMode(boolean on) {
		_isDeleting = on;
		changeDeleteButtonBackgroundDrawable(_isDeleting ? R.drawable.default_button_selector_red
			: R.drawable.default_button_selector);
	}

	private void changeDeleteButtonBackgroundDrawable(int drawable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			_deleteImageButton.setBackground(ContextCompat.getDrawable(getContext(), drawable));
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
		_editImageButton = (ImageButton) findViewById(R.id.comment_edit_button);
		_deleteImageButton = (ImageButton) findViewById(R.id.comment_delete_button);
		_editBodyEditText = (EditText) findViewById(R.id.comment_edit_body);
		_viewSwitcher = (ViewSwitcher) findViewById(R.id.comment_view_switcher);

		_progressBar = (ProgressBar) findViewById(R.id.liferay_progress);
		_contentGroup = (ViewGroup) findViewById(R.id.comment_display_content);
	}

	@Override public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	private TextView _userNameTextView;
	private TextView _bodyTextView;
	private UserPortraitScreenlet _userPortraitScreenlet;
	private TextView _createDateTextView;
	private TextView _editedTextView;
	private ImageButton _editImageButton;
	private ImageButton _deleteImageButton;
	private EditText _editBodyEditText;
	private ViewSwitcher _viewSwitcher;

	private boolean _isDeleting;
	private boolean _isEditing;

	private BaseScreenlet _screenlet;

	private ViewGroup _contentGroup;
	private ProgressBar _progressBar;
}
