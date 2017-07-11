package com.liferay.mobile.screens.westerosemployees_hybrid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;

import com.liferay.mobile.screens.westerosemployees_hybrid.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class DocsCard extends CommentsRatingsCard {

	private boolean loaded;

	public DocsCard(Context context) {
		super(context);
	}

	public DocsCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DocsCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DocsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

	@Override
	public void goLeft() {
		super.goLeft();
	}
}
