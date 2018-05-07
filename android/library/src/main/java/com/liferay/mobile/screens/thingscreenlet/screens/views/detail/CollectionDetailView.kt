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

package com.liferay.mobile.screens.thingscreenlet.screens.views.detail

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.model.Collection
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.adapter.ThingAdapter
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.mobile.sdk.apio.delegates.converter
import com.liferay.mobile.sdk.apio.model.Thing

open class CollectionDetailView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
	RelativeLayout(context, attrs, defStyleAttr), ThingAdapter.Listener {

	override var screenlet: ThingScreenlet? = null

	val recyclerView by bindNonNull<RecyclerView>(R.id.collection_recycler_view)

	override var thing: Thing? by converter<Collection> {
		recyclerView.layoutManager = LinearLayoutManager(context)
		recyclerView.adapter = ThingAdapter(it, this)
	}

	override fun onClickedRow(view: View, thing: Thing): OnClickListener? =
		sendEvent(Event.Click(view, thing))

	override fun onLayoutRow(view: BaseView?, thing: Thing, scenario: Scenario): Int? {
		return sendEvent(Event.FetchLayout(view, thing, scenario))
	}
}
