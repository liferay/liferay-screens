package com.liferay.mobile.screens.bankofwesteros;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * @author Javier Gamarra
 */
public abstract class CardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().setStatusBarColor(getResources().getColor(R.color.westeros_background_gray));

		final View content = findViewById(android.R.id.content);
		content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				content.getViewTreeObserver().removeOnGlobalLayoutListener(this);

				_maxWidth = content.getWidth();
				_maxHeight = content.getHeight();

				heightAndWidthReady();
			}
		});

	}

	protected abstract void heightAndWidthReady();


	protected void goLeft(View leftView, View rightView) {
		leftView.animate().x(0);
		rightView.animate().x(_maxWidth);
	}

	protected void goRight(View leftView, View rightView) {
		leftView.animate().x(-_maxWidth);
		rightView.animate().x(0);
	}

	protected void moveCardToTop(ViewGroup frontCard, ViewGroup backCard) {
		int topPosition = convertDpToPx(18);
		int margin = topPosition / 2;

		frontCard.animate().y(topPosition);

		TransitionManager.beginDelayedTransition(backCard);
		setFrameLayoutMargins(backCard, margin, 0, margin, 0);
		backCard.animate().y(0);
	}

	protected void setFrameLayoutMargins(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
		layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		view.setLayoutParams(layoutParams);
	}

	protected int convertDpToPx(int dp) {
		Resources resources = getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
	}

	protected int _maxWidth;
	protected int _maxHeight;
}
