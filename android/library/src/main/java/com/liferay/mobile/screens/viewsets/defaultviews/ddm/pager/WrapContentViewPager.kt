/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.defaultviews.ddm.pager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * @author Victor Oliveira
 */
class WrapContentViewPager : ViewPager {

	constructor(context: Context) : super(context)

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

	var currentView: ViewGroup? = null
		private set

	public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		var heightMeasure = heightMeasureSpec
		if (currentView == null) {
			super.onMeasure(widthMeasureSpec, heightMeasure)
			return
		}

		currentView?.let { currentView ->

			val makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
			currentView.measure(widthMeasureSpec, makeMeasureSpec)

			val measuredHeight = currentView.measuredHeight

			if (measuredHeight > 0) {
				heightMeasure = View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY)
				super.onMeasure(widthMeasureSpec, heightMeasure)
			}
		}
	}

	fun measureCurrentView(currentView: ViewGroup) {
		this.currentView = currentView
		requestLayout()
	}

	override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
		return false
	}

	override fun onTouchEvent(event: MotionEvent): Boolean {
		return false
	}
}