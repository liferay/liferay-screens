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
import com.liferay.mobile.screens.context.SessionContext
import com.liferay.mobile.screens.testapp.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents

class WorkflowListActivity : AppCompatActivity(), ScreenletEvents {

	private val workflowScreenlet by bindNonNull<ThingScreenlet>(R.id.workflow_screenlet)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.workflow_list_screenlet_activity)

		val tasksUrl = resources.getString(R.string.liferay_server) + getTasksUrl()

		workflowScreenlet.load(tasksUrl)

		workflowScreenlet.screenletEvents = this
	}

	private fun getTasksUrl(): String {
		return "/o/api/p/r/workflow-tasks/assigned-to-me?embedded=comment,blogPost"
	}
}
