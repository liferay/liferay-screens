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
import android.os.Build
import android.support.design.widget.Snackbar
import android.text.Html
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.liferay.apio.consumer.delegates.converter
import com.liferay.apio.consumer.extensions.fullFormat
import com.liferay.apio.consumer.extensions.shortFormat
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.context.SessionContext
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.model.WorkflowTask
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import okhttp3.HttpUrl
import java.util.Date
import java.util.Locale

class WorkflowTaskDetailView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
	RelativeLayout(context, attrs, defStyleAttr) {

	override var screenlet: ThingScreenlet? = null

	private val progressBar by bindNonNull<ProgressBar>(R.id.workflow_progress_bar)
	private val workflowDetail by bindNonNull<RelativeLayout>(R.id.workflow_detail)

	private val assigneeProgressBar by bindNonNull<ProgressBar>(R.id.workflow_image_progress_bar)
	private val assigneeImage by bindNonNull<ThingScreenlet>(R.id.workflow_assignee_image)

	private val assetTitle by bindNonNull<TextView>(R.id.workflow_asset_title)
	private val type by bindNonNull<TextView>(R.id.workflow_type)
	private val dateCreated by bindNonNull<TextView>(R.id.workflow_date_created)
	private val isPending by bindNonNull<FrameLayout>(R.id.workflow_is_pending)
	private val status by bindNonNull<TextView>(R.id.workflow_status)

	private val linearDueDate by bindNonNull<LinearLayout>(R.id.workflow_linear_due_date)
	private val dueDate by bindNonNull<TextView>(R.id.workflow_due_date)

	private val workflowActions by bindNonNull<LinearLayout>(R.id.workflow_actions)

	override var thing: Thing? by converter<WorkflowTask> {
		progressBar.visibility = View.VISIBLE
		workflowDetail.visibility = View.GONE

		getAssetTitle(it)

		assigneeImage.also {
			assigneeProgressBar.visibility = View.VISIBLE
			val url = resources.getString(
				R.string.liferay_server) + "/o/api/p/my-user-account/" + SessionContext.getCurrentUser().id
			it.load(url, onSuccess = { assigneeProgressBar.visibility = View.GONE })
		}

		type.text = resources.getString(R.string.workflow_type_text, it.name.capitalize(), it.resourceType)

		dateCreated.text = if (it.dateCreated != null && isYesterday(it.dateCreated))
			resources.getString(R.string.workflow_yesterday_text)
		else resources.getString(R.string.workflow_created_on) + " " + it.dateCreated?.fullFormat(Locale.getDefault())

		if (it.completed) isPending.visibility = View.GONE

		when {
			it.completed -> status.text = resources.getString(R.string.workflow_completed_text)
			else -> status.text = resources.getString(R.string.workflow_pending_text)
		}

		when {
			it.dueDate != null -> dueDate.text = it.dueDate.shortFormat(Locale.getDefault())
			else -> linearDueDate.visibility = View.GONE
		}

		var transitions = it.transitions

		if (transitions != null && transitions.isNotEmpty()) {
			createActionButtons(transitions)
		}
	}

	private fun createActionButtons(transitions: List<String>) {
		for (transition in transitions) {
			val button = Button(context)
			button.layoutParams = FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT)
			button.text = transition
			workflowActions.addView(button)
		}
	}

	private fun getAssetTitle(workflowTask: WorkflowTask) {
		screenlet?.apioConsumer?.fetch(HttpUrl.parse(workflowTask.identifier!!)!!) { result ->
			result.fold({
				var title = ""

				if (it.type.contains("BlogPosting")) {
					title = it["headline"] as String
				}

				if (it.type.contains("Comment")) {
					title = it["text"] as String
				}

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
					assetTitle.text = Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT)
				} else {
					assetTitle.text = Html.fromHtml(title)
				}

				progressBar.visibility = View.GONE
				workflowDetail.visibility = View.VISIBLE
			}, {
				Snackbar.make(this, "Error", Snackbar.LENGTH_SHORT).show()
			})
		}
	}

	private fun isYesterday(date: Date): Boolean {
		return DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS)
	}
}
