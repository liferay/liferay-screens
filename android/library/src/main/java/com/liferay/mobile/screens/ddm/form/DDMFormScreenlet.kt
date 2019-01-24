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
import android.view.View
import android.widget.FrameLayout
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddm.form.service.APIOGetFormService
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.DDMFormView

/**
 * @author Marcelo Mello
 */
class DDMFormScreenlet @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

	var formInstanceId: Long?
	var layoutId: Int

	var ddmFormView: DDMFormView? = null
	var listener: DDMFormListener? = null

	private val service = APIOGetFormService()

	init {
		val typedArray: TypedArray =
			context.theme.obtainStyledAttributes(attrs, R.styleable.DDMFormScreenlet, defStyleAttr, 0)

		formInstanceId = typedArray.getString(R.styleable.DDMFormScreenlet_formInstanceId)?.toLongOrNull()
		layoutId = typedArray.getString(R.styleable.DDMFormScreenlet_layoutId)?.toIntOrNull()
			?: R.layout.ddm_form_default

		typedArray.recycle()

		ddmFormView = inflate(context, layoutId, null) as DDMFormView
		addView(ddmFormView)
	}

	@JvmOverloads
	fun load(formInstanceId: Long? = this.formInstanceId) {
		formInstanceId?.also {
			val serverUrl = resources.getString(R.string.liferay_server)

			service.getForm(formInstanceId, serverUrl, onSuccess = { thing ->
				ddmFormView?.also { ddmFormView ->
					ddmFormView.thing = thing
					listener?.onFormLoaded(ddmFormView.formInstance)
				}
			}, onError = {
				listener?.onError(it)
			})
		}
	}
}