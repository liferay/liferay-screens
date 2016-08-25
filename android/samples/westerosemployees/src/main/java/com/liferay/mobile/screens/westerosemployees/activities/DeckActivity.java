package com.liferay.mobile.screens.westerosemployees.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.liferay.mobile.screens.westerosemployees.Views.Card;
import com.liferay.mobile.screens.westerosemployees.gestures.FlingListener;
import com.liferay.mobile.screens.westerosemployees.gestures.FlingTouchListener;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * @author Víctor Galán Grande
 */
public class DeckActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onStart() {
		super.onStart();

		calculateSize();
	}

	@Override
	public void onBackPressed() {
		if (cardHistory.isEmpty()) {
			super.onBackPressed();
		} else {
			toPreviousCard();
		}
	}

	protected void calculateSize() {
		if (maxWidth != 0 && maxHeight != 0) {
			onWindowDrawnOnScreens();
		} else {
			final View content = findViewById(android.R.id.content);
			content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						removeObserver();
					} else {
						content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}

					maxWidth = content.getWidth();
					maxHeight = content.getHeight();

					onWindowDrawnOnScreens();
				}

				@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				private void removeObserver() {
					content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			});
		}
	}


	protected int maxWidth;
	protected int maxHeight;
}
