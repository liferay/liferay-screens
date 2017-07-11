package com.liferay.mobile.screens.westerosemployees_hybrid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;

import com.liferay.mobile.screens.westerosemployees_hybrid.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class BlogsCard extends CommentsRatingsCard {

	private boolean loaded;

	public BlogsCard(Context context) {
		super(context);
	}

	public BlogsCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BlogsCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BlogsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public ViewPropertyAnimator setState(CardState state) {
		return super.setState(state);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}
}
