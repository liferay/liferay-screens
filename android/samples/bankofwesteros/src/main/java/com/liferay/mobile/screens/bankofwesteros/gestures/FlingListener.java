package com.liferay.mobile.screens.bankofwesteros.gestures;

/**
 * @author Javier Gamarra
 */
public interface FlingListener {

	//TODO refactor to a method with movement parameter
	void onFlingLeft();

	void onFlingRight();

	void onFlingUp();

	void onFlingDown();

	void onTouch();
}
