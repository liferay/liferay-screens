package com.liferay.mobile.screens.viewsets.defaultviews.comment.add;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.add.view.CommentAddViewModel;

/**
 * @author Alejandro Hernández
 */
public class CommentAddView extends RelativeLayout implements CommentAddViewModel, View.OnClickListener
{
	public CommentAddView(Context context) {
		super(context);
	}

	public CommentAddView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override public void showStartOperation(String actionName) {

	}

	@Override public void showFinishOperation(String actionName) {

	}

	@Override public void showFailedOperation(String actionName, Exception e) {

	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();

		_sendButton = (Button) findViewById(R.id.comment_send);
		_addCommentEditText = (EditText) findViewById(R.id.comment_add);
		_sendButton.setOnClickListener(this);
	}

	@Override public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.comment_send) {
			if (!_addCommentEditText.getText().toString().isEmpty()) {
				_sendButton.setEnabled(false);
				getScreenlet().performUserAction();
			}
		}
	}

	@Override public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	@Override public String getBody() {
		return _addCommentEditText.getText().toString();
	}

	private BaseScreenlet _screenlet;

	private EditText _addCommentEditText;
	private Button _sendButton;
}

