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
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.apio.consumer.model.Thing

sealed class Event<T> {
	class Click(val view: View, val thing: Thing) : Event<View.OnClickListener>()

	class FetchLayout(val view: BaseView? = null, val thing: Thing, val scenario: Scenario) : Event<Int>()

	class CustomEvent(val name: String, val view: BaseView? = null, val thing: Thing): Event<Unit>()

	class ValueChangedEvent @JvmOverloads constructor(val autoSave: Boolean = false)
}
