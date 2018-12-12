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

import com.liferay.apio.consumer.extensions.asDate
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Row
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import java.util.Date

data class WorkflowTask(
	val completed: Boolean,
	val dateCreated: Date?,
	val name: String,
	val dueDate: Date?,
	val resourceType: String?,
	val identifier: String?) {

	companion object {
		val DEFAULT_VIEWS: MutableMap<Scenario, Int> =
			mutableMapOf(
				Row to R.layout.workflow_task_row_default,
				Detail to R.layout.workflow_task_detail_default
			)

		val converter: (Thing) -> Any = {

			val completed = it["completed"] as Boolean

			val dateCreated = (it["dateCreated"] as? String)?.asDate()

			val name = it["name"] as String

			val dueDate = (it["dueDate"] as? String)?.asDate()

			val obj = it["object"] as? Map<String, Any>

			val resourceType = obj?.let {
				it["resourceType"] as? String
			}

			val identifier = obj?.let {
				it["identifier"] as? String
			}

			WorkflowTask(completed, dateCreated, name, dueDate, resourceType, identifier)
		}
	}
}
