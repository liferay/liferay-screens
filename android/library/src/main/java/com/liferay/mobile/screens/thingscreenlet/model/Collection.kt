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

import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.apio.consumer.graph.ApioGraph
import com.liferay.apio.consumer.graph.ApioGraph.get
import com.liferay.apio.consumer.model.Relation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get

data class Collection(val members: List<Thing>?, val totalItems: Int?, val pages: Pages?) {
	companion object {
		val DEFAULT_VIEWS: MutableMap<Scenario, Int> =
			mutableMapOf(
				Detail to R.layout.collection_detail_default
			)

		val converter: (Thing) -> Any = { it: Thing ->
			val member = (it["member"] as? List<Relation>)?.mapNotNull {
				ApioGraph[it.id]?.value
			}

			val totalItems = (it["totalItems"] as? Double)?.toInt()

			val nextPage = (it["view"] as Relation)["next"] as? String

			val pages = nextPage?.let(::Pages)

			Collection(member, totalItems, pages)
		}
	}
}

data class Pages(val next: String?)
