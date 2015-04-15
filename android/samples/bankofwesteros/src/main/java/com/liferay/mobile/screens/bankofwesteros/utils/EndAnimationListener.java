package com.liferay.mobile.screens.bankofwesteros.utils;

import android.animation.Animator;

/**
 * @author Javier Gamarra
 */
public abstract class EndAnimationListener implements Animator.AnimatorListener {

	public abstract void onAnimationEnd(Animator animator);

	public void onAnimationStart(Animator animation) {

	}

	public void onAnimationCancel(Animator animation) {

	}

	public void onAnimationRepeat(Animator animation) {

	}
}
