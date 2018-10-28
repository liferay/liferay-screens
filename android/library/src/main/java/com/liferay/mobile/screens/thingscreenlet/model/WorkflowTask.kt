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
import com.liferay.apio.consumer.graph
import com.liferay.apio.consumer.model.Relation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.screens.views.Row
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import java.util.*

data class WorkflowTask(
	val completed: Boolean,
	val dateCreated: Date?,
	val name: String,
	val expires: Date?,
	val comment: Comment?,
	val blogPost: BlogPosting?) {

	companion object {
		val DEFAULT_VIEWS: MutableMap<Scenario, Int> =
			mutableMapOf(
				Row to R.layout.workflow_task_row_default
			)

		val converter: (Thing) -> Any = {

			val completed = it["completed"] as Boolean

			val dateCreated = (it["dateCreated"] as? String)?.asDate()

			val name = it["name"] as String

			val expires = (it["expires"] as? String)?.asDate()

			val comment = (it["comment"] as? Relation)?.let {
				getComment(it)
			}

			val blogPost = (it["blogPost"] as? Relation)?.let {
				getBlogPost(it)
			}

			WorkflowTask(completed, dateCreated, name, expires, comment, blogPost)
		}

		private fun getComment(it: Relation): Comment? {
			val text = it["text"] as? String

			val type = graph[it.id]?.value?.type?.get(0)

			return Comment(text, type)
		}

		private fun getBlogPost(it: Relation): BlogPosting? {

			val headline = it["headline"] as? String

			val alternativeHeadline = it["alternativeHeadline"] as? String

			val articleBody = it["articleBody"] as? String

			val creator = it["creator"] as? Relation

			val dateCreated = (it["dateCreated"] as? String)?.asDate()

			val type = graph[it.id]?.value?.type?.get(0)

			return BlogPosting(headline, alternativeHeadline, articleBody, creator, dateCreated, type)
		}
	}
}
