package com.liferay.mobile.screens.bankofwesteros;

import android.animation.Animator;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

import com.daimajia.swipe.SwipeLayout;
import com.liferay.mobile.screens.bankofwesteros.gestures.FlingListener;
import com.liferay.mobile.screens.bankofwesteros.gestures.FlingTouchListener;

/**
 * @author Javier Gamarra
 */
public class IssuesActivity extends CardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issues);


		_background = findViewById(R.id.background);

		_card1 = (LinearLayout) findViewById(R.id.card1);
		_card1.setOnTouchListener(new FlingTouchListener(this, getCard1Listener()));
		_card2 = (LinearLayout) findViewById(R.id.card2);
		_card2.setOnTouchListener(new FlingTouchListener(this, getCard2Listener()));


	}

	@Override
	protected void heightAndWidthReady() {
		_background.setY(_maxHeight);
		_card1.setY(_maxHeight);
		_card2.setY(_maxHeight);

		_card1.animate().y(0);
		_card2Position = _maxHeight - getResources().getDimensionPixelSize(R.dimen.icon_height);
		_card2.animate().y(_card2Position).setListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				_background.setY(0);
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
	}

	private void toMenu() {
		int restPosition = _maxHeight - 2 * getResources().getDimensionPixelSize(R.dimen.icon_height);
		_card1.animate().y(restPosition);
	}

	private void toIssues() {
		TransitionManager.beginDelayedTransition(_card1);
		setFrameLayoutMargins(_card1, 0, 0, 0, 0);
		_card1.animate().y(0);
		_card2.animate().y(_card2Position);
	}

	private void toNewIssue() {
		moveCardToTop(_card2, _card1);
	}

	private FlingListener getCard1Listener() {
		return new FlingListener() {
			@Override
			public void onFlingLeft() {
			}

			@Override
			public void onFlingRight() {
			}

			@Override
			public void onFlingUp() {
				toIssues();
			}

			@Override
			public void onFlingDown() {
				toMenu();
			}

			@Override
			public void onTouch() {
				toIssues();
			}
		};
	}

	private FlingListener getCard2Listener() {
		return new FlingListener() {
			@Override
			public void onFlingLeft() {
			}

			@Override
			public void onFlingRight() {
			}

			@Override
			public void onFlingUp() {
				toNewIssue();
			}

			@Override
			public void onFlingDown() {
				toIssues();
			}

			@Override
			public void onTouch() {
				toNewIssue();
			}
		};
	}

	private int _card2Position;

	private View _background;
	private LinearLayout _card1;
	private LinearLayout _card2;
}
