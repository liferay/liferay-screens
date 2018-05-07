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
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.thingscreenlet.model.Person
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.mobile.sdk.apio.delegates.converter
import com.liferay.mobile.sdk.apio.extensions.mediumFormat
import com.liferay.mobile.sdk.apio.model.Thing

class PersonDetailView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
	RelativeLayout(context, attrs, defStyleAttr) {

	override var screenlet: ThingScreenlet? = null

	val name by bindNonNull<TextView>(R.id.person_name)
	val email by bindNonNull<TextView>(R.id.person_email)
	val jobTitle by bindNonNull<TextView>(R.id.person_job_title)
	val birthDate by bindNonNull<TextView>(R.id.person_birthDate)

	override var thing: Thing? by converter<Person> {
		name.text = it.name
		email.text = it.email
		jobTitle.text = it.jobTitle
		birthDate.text = it.birthDate?.mediumFormat()
	}
}
