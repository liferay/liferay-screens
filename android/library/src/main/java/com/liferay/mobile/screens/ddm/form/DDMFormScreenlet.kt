/*
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
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.util.LiferayLogger
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.DDMFormView

/**
 * @author Marcelo Mello
 */
class DDMFormScreenlet @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    var formInstanceId: Long
    var listener: DDMFormListener? = null
    val screenlet: ThingScreenlet

    init {
        val typedArray: TypedArray =
            context.getTheme().obtainStyledAttributes(attrs, R.styleable.DDMFormScreenlet, 0, 0)

        formInstanceId =
            castToLong(typedArray.getString(R.styleable.DDMFormScreenlet_formInstanceId))

        typedArray.recycle()

        screenlet = ThingScreenlet(context, attrs, defStyleAttr)
        addView(screenlet)
    }

    fun load() {
        val url: String = getResourcePath()

        screenlet.load(url, Detail, onSuccess = {
            (it.baseView as? DDMFormView)?.let { ddmFormView ->
                listener?.onFormLoaded(ddmFormView.formInstance)
            }
        }, onError = {
            listener?.onError(it)
        })
    }

    private fun getResourcePath(): String {
        val serverUrl = getResources().getString(R.string.liferay_server)
        return serverUrl + String.format(FormConstants.URL_TEMPLATE, formInstanceId)
    }

    /**
     * TODO: Methods below are copied from BaseScreenlet.java class.
     * Need to think another approach for these methods
     */
    protected fun castToLong(value: String?): Long {
        return castToLongOrUseDefault(value, 0)
    }

    protected fun castToLongOrUseDefault(value: String?, defaultValue: Long): Long {
        if (value == null) {
            return defaultValue
        }

        try {
            return java.lang.Long.parseLong(value)
        } catch (e: NumberFormatException) {
            LiferayLogger.e("You have supplied a string and we expected a long number", e)
            throw e
        }
    }

}