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

package com.liferay.mobile.screens.thingscreenlet.screens.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.thingscreenlet.screens.views.Row
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.mobile.sdk.apio.delegates.observeNonNull
import com.liferay.mobile.sdk.apio.model.Thing

open class
ThingViewHolder(itemView: View, listener: Listener) : RecyclerView.ViewHolder(itemView), ScreenletEvents {

	val thingScreenlet by bindNonNull<ThingScreenlet>(R.id.thing_screenlet)

	open var thing: Thing? by observeNonNull {
		itemView.setOnClickListener { view ->
			listener.onClickedRow(view, it)?.onClick(itemView)
		}

		thingScreenlet.scenario = Row

		thingScreenlet.screenletEvents = object : ScreenletEvents {
			override fun <T : BaseView> onGetCustomLayout(screenlet: ThingScreenlet, parentView: T?, thing: Thing,
				scenario: Scenario): Int? {
				return listener.onLayoutRow(screenlet.baseView, thing, scenario)
			}
		}

		thingScreenlet.thing = thing
	}

	interface Listener {
		fun onClickedRow(view: View, thing: Thing): View.OnClickListener?
		fun onLayoutRow(view: BaseView?, thing: Thing, scenario: Scenario): Int?
	}

}
