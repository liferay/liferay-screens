package com.liferay.mobile.screens.viewsets.defaultviews.comment.display;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Alejandro Hernández
 */
public class CommentView extends RelativeLayout implements CommentDisplayViewModel
{
	public CommentView(Context context) {
		super(context);
	}

	public CommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override public void showFinishOperation(final CommentEntry commentEntry) {
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
	}

	@Override public void showStartOperation(String actionName) {

	}

	@Override public void showFinishOperation(String actionName) {

	}

	@Override public void showFailedOperation(String actionName, Exception e) {

	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();

		_userNameTextView = (TextView) findViewById(R.id.comment_user_name);
		_bodyTextView = (TextView) findViewById(R.id.comment_body);
		_userPortraitScreenlet = (UserPortraitScreenlet) findViewById(R.id.comment_user_portrait);
		_createDateTextView = (TextView) findViewById(R.id.comment_create_date);
		_editedTextView = (TextView) findViewById(R.id.comment_edited);
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

	private BaseScreenlet _screenlet;
}
