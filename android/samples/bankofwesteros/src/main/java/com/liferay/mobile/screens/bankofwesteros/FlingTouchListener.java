package com.liferay.mobile.screens.bankofwesteros;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Javier Gamarra
 */
public class FlingTouchListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

	public FlingTouchListener(Context context, FlingListener listener) {
		_gestureDetector = new GestureDetector(context, new FlingDetector());
		_listener = listener;
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		_gestureDetector.onTouchEvent(event);
		return true;
	}

	private class FlingDetector extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e2.getY() - e1.getY() > SWIPE_MOVEMENT_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
				_listener.onFlingDown();
			}
			else {
				_listener.onFlingUp();
			}
			return true;
		}
	}

	private GestureDetector _gestureDetector;
	private FlingListener _listener;

	private static final float SWIPE_VELOCITY_THRESHOLD = 1f;
	private static final float SWIPE_MOVEMENT_THRESHOLD = 10f;
}
