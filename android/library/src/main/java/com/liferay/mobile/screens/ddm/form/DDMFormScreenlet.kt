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

package com.liferay.mobile.screens.ddm.form

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.FrameLayout
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.util.getLong
import com.liferay.mobile.screens.util.use
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.DDMFormView

/**
 * @author Marcelo Mello
 */
class DDMFormScreenlet @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: FrameLayout(context, attrs, defStyleAttr) {

	var formInstanceId: Long? = null
	var listener: DDMFormListener? = null
	val screenlet: ThingScreenlet

	init {
		val typedArray: TypedArray =
			context.theme.obtainStyledAttributes(attrs, R.styleable.DDMFormScreenlet, defStyleAttr, 0)

		typedArray.use {
			formInstanceId = getLong(R.styleable.DDMFormScreenlet_formInstanceId)
		}

		screenlet = ThingScreenlet(context, attrs, defStyleAttr)
		addView(screenlet)
	}

	@JvmOverloads
	fun load(formInstanceId: Long? = this.formInstanceId) {
		formInstanceId?.also {
			val thingId = getResourcePath(it)

			screenlet.load(thingId) { result ->
				result.success { thingScreenlet ->
					(thingScreenlet.baseView as? DDMFormView)?.let { ddmFormView ->
						listener?.onFormLoaded(ddmFormView.formInstance)
					}
				}

				result.failure { exception ->
					listener?.onError(exception)
				}
			}
		}
	}

	private fun getResourcePath(formInstanceId: Long): String {
		val serverUrl = resources.getString(R.string.liferay_server)
		return serverUrl + String.format(FormConstants.URL_TEMPLATE, formInstanceId)
	}
}