package com.liferay.mobile.screens.bankofwesteros.gestures;

/**
 * @author Javier Gamarra
 */
public interface FlingListener {

	enum Movement {
		UP, LEFT, RIGHT, DOWN, TOUCH
	}

	void onFling(Movement movement);
}
