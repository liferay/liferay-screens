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

package com.liferay.mobile.screens.testapp.postings.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.liferay.apio.consumer.ApioConsumer
import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.testapp.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.util.LiferayLogger
import org.jetbrains.anko.startActivity

class DetailActivity : AppCompatActivity(), ScreenletEvents {

	val thingScreenlet by bindNonNull<ThingScreenlet>(R.id.thing_screenlet_activity)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.thing_screenlet_activity)

		val id = intent.getStringExtra("id")

		thingScreenlet.screenletEvents = this

		thingScreenlet.load(id, scenario = Detail)
	}

	override fun <T : BaseView> onClickEvent(baseView: T, view: View, thing: Thing) = View.OnClickListener {
		startActivity<DetailActivity>("id" to thing.id)
	}

	override fun <T : BaseView> onCustomEvent(name: String, screenlet: ThingScreenlet, parentView: T?, thing: Thing) {
		thing.operations.keys.firstOrNull {
			it.contains(name)
		}?.let {
			thing.operations[it]
		}?.also { operation ->
			val values = thing.attributes.filterValues { it is String }

			val apioConsumer = thingScreenlet.apioConsumer

			operation.form?.id?.let { expects ->
				apioConsumer.getOperationForm(expects) { result ->
					result.success { properties ->
						startActivity<EditActivity>(
							"properties" to properties.map { it.name },
							"values" to values,
							"id" to thing.id, "operation" to operation.id
						)
					}
				}
			} ?: run {
				apioConsumer.performOperation(thing.id, operation.id) { result ->
					result.failure {
						LiferayLogger.e(it.message, it)
					}
				}
			}
		}
	}

}
