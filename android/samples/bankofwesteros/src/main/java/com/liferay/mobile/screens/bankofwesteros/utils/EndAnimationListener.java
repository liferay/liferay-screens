/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.bankofwesteros.utils;

import android.animation.Animator;

/**
 * @author Javier Gamarra
 */
public abstract class EndAnimationListener implements Animator.AnimatorListener {

    /**
     * Notifies the end of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.
     */
    public abstract void onAnimationEnd(Animator animator);

    /**
     * Notifies the start of the animation.
     */
    public void onAnimationStart(Animator animation) {

    }

    /**
     * Notifies the cancellation of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.
     */
    public void onAnimationCancel(Animator animation) {

    }

    /**
     * Notifies the repetition of the animation.
     */
    public void onAnimationRepeat(Animator animation) {

    }
}
