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

package com.liferay.mobile.screens.thingscreenlet.model

import com.liferay.apio.consumer.graph
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario

data class Comment(
	val text: String?,
	val type: String?) {

	companion object {
		val DEFAULT_VIEWS: MutableMap<Scenario, Int> =
			mutableMapOf(
			)

		val converter: (Thing) -> Any = {

			val text = it["text"] as String

			val type = graph[it.id]?.value?.type?.get(0)

			Comment(text, type)
		}
	}
}
