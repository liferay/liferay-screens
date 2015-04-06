package com.liferay.mobile.screens.bankofwesteros.gestures;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Javier Gamarra
 */
public class FlingTouchListener implements View.OnTouchListener {

	public FlingTouchListener(Context context, FlingListener flingListener) {
		_gestureDetector = new GestureDetector(context, new GestureDetectorListener());
		_flingListener = flingListener;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		_gestureDetector.onTouchEvent(event);
		return true;
	}

	private class GestureDetectorListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			_flingListener.onFling(FlingListener.Movement.TOUCH);
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float swipeY = e2.getY() - e1.getY();
			float swipeX = e2.getX() - e1.getX();
			if (Math.abs(swipeY) > SWIPE_MOVEMENT_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
				if (swipeY > 0) {
					_flingListener.onFling(FlingListener.Movement.DOWN);
				}
				else {
					_flingListener.onFling(FlingListener.Movement.UP);
				}
			}
			else if (Math.abs(e2.getX() - e1.getX()) > SWIPE_MOVEMENT_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
				if (swipeX > 0) {
					_flingListener.onFling(FlingListener.Movement.LEFT);
				}
				else {
					_flingListener.onFling(FlingListener.Movement.RIGHT);
				}
			}

			return true;
		}
	}

	private GestureDetector _gestureDetector;
	private FlingListener _flingListener;

	private static final float SWIPE_VELOCITY_THRESHOLD = 10f;
	private static final float SWIPE_MOVEMENT_THRESHOLD = 100f;
}
