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
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.model.Collection
import com.liferay.mobile.screens.testapp.R
import com.liferay.mobile.screens.thingscreenlet.model.BlogPosting
import com.liferay.mobile.screens.thingscreenlet.model.Person
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents
import com.liferay.mobile.screens.thingscreenlet.screens.views.*
import com.liferay.mobile.sdk.apio.model.Thing
import com.liferay.mobile.sdk.apio.model.get
import okhttp3.Credentials
import org.jetbrains.anko.startActivity

class ThingMainActivity : AppCompatActivity(), ScreenletEvents {

	private val thingScreenlet by bindNonNull<ThingScreenlet>(R.id.thing_screenlet_activity)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.thing_screenlet_activity)

		registerScenarios()

//		val id = "http://192.168.56.1:8080/p/blog-postings"
		val id = "https://apiosample.wedeploy.io/p/blog-postings"

		thingScreenlet.load(id, Credentials.basic("test@liferay.com", "test"))

		thingScreenlet.screenletEvents = this
	}

	private fun registerScenarios() {
		Scenario.stringToScenario = {
			if (it == "detail-small") DetailSmall else null
		}

		Person.DEFAULT_VIEWS[Custom("portrait")] = R.layout.person_portrait_custom
		Person.DEFAULT_VIEWS[DetailSmall] = R.layout.person_detail_small
		Person.DEFAULT_VIEWS[Detail] = R.layout.person_detail_custom
		Person.DEFAULT_VIEWS[Custom("portrait_small")] = R.layout.person_portrait_small
		Person.DEFAULT_VIEWS[Custom("portrait_big")] = R.layout.person_portrait_big

		BlogPosting.DEFAULT_VIEWS[Row] = R.layout.blog_posting_row_custom
		BlogPosting.DEFAULT_VIEWS[Detail] = R.layout.blog_posting_detail_custom

		Collection.DEFAULT_VIEWS[Detail] = R.layout.collection_detail_custom
	}

	override fun <T : BaseView> onClickEvent(baseView: T, view: View, thing: Thing) = View.OnClickListener {
		startActivity<DetailActivity>("id" to thing.id)
	}

	override fun <T : BaseView> onGetCustomLayout(screenlet: ThingScreenlet, parentView: T?, thing: Thing,
		scenario: Scenario): Int? =
		if (thing["headline"] == "My blog") R.layout.blog_posting_row_by_id
		else super.onGetCustomLayout(screenlet, parentView, thing, scenario)

}


object DetailSmall : Scenario
