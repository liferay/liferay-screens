package com.liferay.mobile.screens.westerosemployees_hybrid.views;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;

import com.liferay.mobile.screens.context.LiferayScreensContext;

/**
 * @author Víctor Galán Grande
 */
public abstract class CommentsRatingsCard extends Card {

	public CommentsRatingsCard(Context context) {
		super(context);
	}

	public CommentsRatingsCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CommentsRatingsCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CommentsRatingsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	private void hideSoftKeyBoard() {
		Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
		InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

		if (imm.isAcceptingText()) {
			IBinder windowToken = activity.getCurrentFocus().getWindowToken();

			if (windowToken != null) {
				imm.hideSoftInputFromWindow(windowToken, 0);
			}
		}
	}
}
