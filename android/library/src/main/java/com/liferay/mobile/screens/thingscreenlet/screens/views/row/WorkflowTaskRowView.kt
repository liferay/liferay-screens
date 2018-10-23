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

package com.liferay.mobile.screens.thingscreenlet.screens.views.row

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.liferay.apio.consumer.delegates.converter
import com.liferay.apio.consumer.extensions.shortFormat
import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.model.WorkflowTask
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import java.util.*
import java.util.concurrent.TimeUnit


class WorkflowTaskRowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
    LinearLayout(context, attrs, defStyleAttr) {

    override var screenlet: ThingScreenlet? = null

    private val isPending by bindNonNull<FrameLayout>(R.id.workflow_is_pending)
    private val assetTitle by bindNonNull<TextView>(R.id.workflow_asset_title)
    private val dateCreated by bindNonNull<TextView>(R.id.workflow_date_created)
    private val type by bindNonNull<TextView>(R.id.workflow_type)
    private val status by bindNonNull<TextView>(R.id.workflow_status)

    override var thing: Thing? by converter<WorkflowTask> {

        if (it.completed) isPending.visibility = View.INVISIBLE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            assetTitle.text = Html.fromHtml(getAssetTitle(it), Html.FROM_HTML_MODE_COMPACT)
        } else {
            assetTitle.text = Html.fromHtml(getAssetTitle(it))
        }

        dateCreated.text = if (it.dateCreated != null && isYesterday(it.dateCreated))
            resources.getString(R.string.workflow_yesterday_text)
        else it.dateCreated?.shortFormat(Locale.getDefault())

        type.text = resources.getString(R.string.workflow_type_text, it.name.capitalize(), getAssetName(it))

        when {
            it.completed -> status.text = resources.getString(R.string.workflow_completed_text)
            it.expires != null -> {
                val days = getDays(it.expires)

                if (days < 0) status.text = resources.getQuantityString(R.plurals.workflow_due_date_before_text,
                    Math.abs(days), Math.abs(days))
                else status.text = resources.getQuantityString(R.plurals.workflow_due_date_after_text, days, days)
            }
            else -> status.text = resources.getString(R.string.workflow_pending_text)
        }
    }

    private fun getAssetName(workflowTask: WorkflowTask): String {
        return workflowTask.comment?.type ?: workflowTask.blogPost?.type ?: "Undefined"
    }

    private fun getAssetTitle(workflowTask: WorkflowTask): String {
        return workflowTask.comment?.text ?: workflowTask.blogPost?.headline ?: "Undefined"
    }

    private fun getDays(expires: Date): Int {
        val today = Calendar.getInstance().timeInMillis
        val dueDate = expires.time

        val diff = dueDate - today

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }

    private fun isYesterday(date: Date): Boolean {
        return DateUtils.isToday(date.time + DateUtils.DAY_IN_MILLIS)
    }
}
