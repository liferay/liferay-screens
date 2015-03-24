package com.liferay.mobile.screens.bankofwesteros;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author Javier Gamarra
 */
public class IssuesActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issues);


		_background = findViewById(R.id.background);

		_card1 = (LinearLayout) findViewById(R.id.card1);
		_card1.setOnTouchListener(new FlingTouchListener(this, getCard1Listener()));
		_card2 = (LinearLayout) findViewById(R.id.card2);
		_card2.setOnTouchListener(new FlingTouchListener(this, getCard2Listener()));

		_card1.setY(2000);
		_card2.setY(2000);

		_card1.animate().y(0);
		_card2.animate().y(1500);
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
