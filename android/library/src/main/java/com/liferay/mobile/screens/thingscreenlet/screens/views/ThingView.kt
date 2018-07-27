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

package com.liferay.mobile.screens.thingscreenlet.screens.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bind
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.apio.consumer.delegates.observeNonNull
import com.liferay.apio.consumer.model.Thing

class ThingView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
	LinearLayout(context, attrs, defStyleAttr) {

	override var screenlet: ThingScreenlet? = null

	val thingId by bind<View>(R.id.thing_id)
	val thingType by bind<View>(R.id.thing_type)
	val thingName by bind<View>(R.id.thing_name)

	override var thing: Thing? by observeNonNull {
		(thingId as? TextView)?.text = it.id
		(thingType as? TextView)?.text = it.type.joinToString()
		(thingName as? TextView)?.text = it.name
	}

}
