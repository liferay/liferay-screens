package com.liferay.mobile.screens.viewsets.defaultviews.comment.add;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
public class CommentAddView extends RelativeLayout implements CommentAddViewModel, View.OnClickListener {

	public CommentAddView(Context context) {
		super(context);
	}

	public CommentAddView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentAddView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public CommentAddView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void showStartOperation(String actionName) {

	}

	@Override
	public void showFinishOperation(String actionName) {

	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		sendButton = (Button) findViewById(R.id.comment_send);
		addCommentEditText = (EditText) findViewById(R.id.comment_add);
		sendButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.comment_send) {
			if (!addCommentEditText.getText().toString().isEmpty()) {
				sendButton.setEnabled(false);
				getScreenlet().performUserAction();
			}
		}
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	@Override
	public String getBody() {
		return addCommentEditText.getText().toString();
	}

	private BaseScreenlet screenlet;

	private EditText addCommentEditText;
	private Button sendButton;
}

