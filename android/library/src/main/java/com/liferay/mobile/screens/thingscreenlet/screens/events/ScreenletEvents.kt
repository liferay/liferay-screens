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

package com.liferay.mobile.screens.thingscreenlet.screens.events

import android.view.View
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.apio.consumer.model.Thing

interface ScreenletEvents {
	fun <T : BaseView> onClickEvent(baseView: T, view: View, thing: Thing): View.OnClickListener? = null

	fun <T : BaseView> onGetCustomLayout(
	screenlet: ThingScreenlet, parentView: T?, thing: Thing, scenario: Scenario): Int? = null

	fun <T: BaseView> onCustomEvent(name: String, screenlet: ThingScreenlet, parentView: T?, thing: Thing) = Unit
}
