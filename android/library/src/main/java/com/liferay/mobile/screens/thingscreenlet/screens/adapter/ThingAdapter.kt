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

import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import com.liferay.apio.consumer.ApioConsumer
import com.liferay.mobile.screens.thingscreenlet.extensions.inflate
import com.liferay.mobile.screens.thingscreenlet.model.Collection
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.apio.consumer.delegates.convert
import com.liferay.apio.consumer.model.Thing
import okhttp3.HttpUrl

class ThingAdapter(collection: Collection, val listener: Listener) :
	Adapter<ThingViewHolder>(), ThingViewHolder.Listener {

	override fun onLayoutRow(view: BaseView?, thing: Thing, scenario: Scenario) =
		listener.onLayoutRow(view, thing, scenario)

	override fun onClickedRow(view: View, thing: Thing): View.OnClickListener? = listener.onClickedRow(view, thing)

	val totalItems = collection.totalItems
	val members = collection.members?.toMutableList() ?: mutableListOf()

	val nextPage = collection.pages?.next

	override fun onBindViewHolder(holder: ThingViewHolder, position: Int) {
		if (members.size > position) {
			holder.thing = members[position]
		} else {
			nextPage?.let { nextPage ->
				HttpUrl.parse(nextPage)?.let { httpUrl ->
					ApioConsumer().fetch(httpUrl, onSuccess = { thing ->
						convert<Collection>(thing)?.let {
							val moreMembers = it.members
							merge(members, moreMembers)
							notifyDataSetChanged()
						}
					}, onError = { })
				}
			}
		}
	}

	override fun getItemViewType(position: Int): Int {
		return position
	}

	private fun merge(members: MutableList<Thing>, moreMembers: List<Thing>?) {
		moreMembers?.apply { members.addAll(this) }
	}

	override fun getItemCount(): Int = totalItems ?: 0

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingViewHolder {
		return ThingViewHolder(parent.inflate(R.layout.thing_viewholder_default), this)
	}

	interface Listener {
		fun onClickedRow(view: View, thing: Thing): View.OnClickListener?
		fun onLayoutRow(view: BaseView?, thing: Thing, scenario: Scenario): Int?
	}
}
