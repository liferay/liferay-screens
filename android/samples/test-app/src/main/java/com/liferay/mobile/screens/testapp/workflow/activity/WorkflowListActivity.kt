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

package com.liferay.mobile.screens.testapp.workflow.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.context.SessionContext
import com.liferay.mobile.screens.testapp.R
import com.liferay.mobile.screens.testapp.postings.activity.DetailActivity
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import org.jetbrains.anko.startActivity

class WorkflowListActivity : AppCompatActivity(), ScreenletEvents {

	private val workflowScreenlet by bindNonNull<ThingScreenlet>(R.id.workflow_screenlet)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.workflow_list_screenlet_activity)

		val credentials = SessionContext.getCredentialsFromCurrentSession()

		val tasksUrl = resources.getString(R.string.liferay_server) + getTasksUrl()

		workflowScreenlet.load(tasksUrl, credentials = credentials)

		workflowScreenlet.screenletEvents = this
	}

	override fun <T : BaseView> onClickEvent(baseView: T, view: View, thing: Thing) = View.OnClickListener {
		startActivity<DetailActivity>("id" to thing.id)
	}

	private fun getTasksUrl(): String {
		return "/o/api/p/r/workflow-tasks/assigned-to-me"
	}
}
