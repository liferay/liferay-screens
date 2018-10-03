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

package com.liferay.mobile.screens.thingscreenlet.screens

import android.content.Context
import android.util.AttributeSet
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.context.SessionContext
import com.liferay.mobile.screens.thingscreenlet.screens.views.Custom
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Row
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario

open class WorkflowListScreenlet @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    ThingScreenlet(context, attrs, defStyleAttr, defStyleRes) {

    override var scenario: Scenario = Row

    private var tasksType: Int

    init {

        val typedArray = attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.WorkflowListScreenlet, 0, 0)
        }

        layoutId = typedArray?.getResourceId(R.styleable.WorkflowListScreenlet_layoutId, 0) ?: 0

        tasksType = typedArray?.getInt(R.styleable.WorkflowListScreenlet_tasks, 0) ?: 0

        val scenarioId = typedArray?.getString(R.styleable.WorkflowListScreenlet_scenario) ?: ""

        scenario = Scenario.stringToScenario?.invoke(scenarioId) ?: when (scenarioId.toLowerCase()) {
            "detail", "" -> Detail
            "row" -> Row
            else -> Custom(scenarioId)
        }
    }


    open fun getTasksUrl(): String {

        val server = resources.getString(R.string.liferay_server)

        return if (tasksType == 1) {
            // TODO: Workflow tasks assigned to my roles (not working)
            "$server/o/api/p/roles/20105/workflow-tasks"
        } else {
            "$server/o/api/p/my-user-account/" + SessionContext.getCurrentUser().id + "/workflow-tasks?embedded=comment,blogPost"
        }
    }
}
