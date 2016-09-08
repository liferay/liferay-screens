package com.liferay.mobile.screens.westerosemployees.gestures;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.liferay.mobile.screens.westerosemployees.Views.Card;
import com.liferay.mobile.screens.westerosemployees.utils.ViewUtil;

/**
 * @author Javier Gamarra
 */
public class FlingTouchListener implements View.OnTouchListener {

	public FlingTouchListener(Context context, FlingListener flingListener) {
		gestureDetector = new GestureDetector(context, new GestureDetectorListener());
		this.flingListener = flingListener;
		cardSizeMinimizedPx = ViewUtil.pixelFromDp(context, Card.CARD_SIZE);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return true;
	}

	private class GestureDetectorListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (e.getY() >= cardSizeMinimizedPx) {
				return true;
			}
			flingListener.onFling(FlingListener.Movement.TOUCH);
			return true;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e1.getY() >= cardSizeMinimizedPx && e2.getY() >= cardSizeMinimizedPx) {
				return true;
			}
			float swipeY = e2.getY() - e1.getY();
			float swipeX = e2.getX() - e1.getX();
			if (Math.abs(swipeY) > SWIPE_MOVEMENT_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
				if (swipeY > 0) {
					flingListener.onFling(FlingListener.Movement.DOWN);
				} else {
					flingListener.onFling(FlingListener.Movement.UP);
				}
			} else if (Math.abs(e2.getX() - e1.getX()) > SWIPE_MOVEMENT_THRESHOLD
				&& Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
				if (swipeX > 0) {
					flingListener.onFling(FlingListener.Movement.LEFT);
				} else {
					flingListener.onFling(FlingListener.Movement.RIGHT);
				}
			}

			return true;
		}
	}
	private static final float SWIPE_VELOCITY_THRESHOLD = 10f;
	private static final float SWIPE_MOVEMENT_THRESHOLD = 100f;
	private GestureDetector gestureDetector;
	private FlingListener flingListener;
	private int cardSizeMinimizedPx;
}
