/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.util.extensions

import android.content.res.TypedArray
import androidx.annotation.StyleableRes
import android.util.AttributeSet
import android.view.View

/**
 * @author Paulo Cruz
 * @author Victor Oliveira
 */
fun List<View?>.firstNotNull(): View? = this.filterNotNull().firstOrNull()

fun View.getStyledAttributes(set: AttributeSet? = null, @StyleableRes attrs: IntArray): TypedArray? {
	return context.theme.obtainStyledAttributes(set, attrs, 0, 0)
}

fun View.setVisibility(isVisible: Boolean?) {
	visibility = if (isVisible != false) View.VISIBLE else View.GONE
}