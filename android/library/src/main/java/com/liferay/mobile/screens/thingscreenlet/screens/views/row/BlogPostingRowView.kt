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

package com.liferay.mobile.screens.thingscreenlet.screens.views.row

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.model.BlogPosting
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.sdk.apio.delegates.converter
import com.liferay.mobile.sdk.apio.extensions.fullFormat
import com.liferay.mobile.sdk.apio.model.Thing

class BlogPostingRowView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
	FrameLayout(context, attrs, defStyleAttr) {

	override var screenlet: ThingScreenlet? = null

	val headline by bindNonNull<TextView>(R.id.headline)
	val creator by bindNonNull<ThingScreenlet>(R.id.creator_avatar)
	val createDate by bindNonNull<TextView>(R.id.create_date)

	override var thing: Thing? by converter<BlogPosting> {
		headline.text = it.headline

		it.creator?.also {
			creator.load(it.id)
		}

		it.createDate?.also {
			createDate.text = it.fullFormat()
		}

		if (it.createDate == null) {
			createDate.visibility = GONE
		}
	}
}
