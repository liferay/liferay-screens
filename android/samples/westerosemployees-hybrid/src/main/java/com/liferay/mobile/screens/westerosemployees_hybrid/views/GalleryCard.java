package com.liferay.mobile.screens.westerosemployees_hybrid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;

import com.liferay.mobile.screens.westerosemployees_hybrid.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class GalleryCard extends CommentsRatingsCard {

	public GalleryCard(Context context) {
		super(context);
	}

	public GalleryCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GalleryCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
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
