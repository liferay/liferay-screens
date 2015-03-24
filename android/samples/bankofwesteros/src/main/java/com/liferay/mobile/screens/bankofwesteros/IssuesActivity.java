package com.liferay.mobile.screens.bankofwesteros;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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

		findViewById(R.id.issues).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toMenu();
			}
		});

		_card1.animate().y(0);
		int iconHeightInDp = getResources().getDimensionPixelSize(R.dimen.icon_height);
		_card2.animate().y(_maxHeight - iconHeightInDp).setListener(new Animator.AnimatorListener() {
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
		_card1.animate().y(600);
	}

	private void toIssues() {
		_card1.animate().y(0);
		_card2.animate().y(1500);
	}

	private void toNewIssue() {
		_card2.animate().y(100);
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


	private View _background;
	private LinearLayout _card1;
	private LinearLayout _card2;
}
