package com.liferay.mobile.screens.viewsets.defaultviews.comment.add;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.comment.add.view.CommentAddViewModel;

/**
 * @author Alejandro Hernández
 */
public class CommentAddView extends RelativeLayout implements CommentAddViewModel
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
	}

	@Override public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	@Override public String getBody() {
	}

	private BaseScreenlet _screenlet;
}

