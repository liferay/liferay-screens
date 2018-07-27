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

package com.liferay.mobile.screens.testapp.postings.view

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.liferay.mobile.screens.thingscreenlet.delegates.bindNonNull
import com.liferay.mobile.screens.testapp.R
import com.liferay.mobile.screens.thingscreenlet.model.Person
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView
import com.liferay.apio.consumer.delegates.converter
import com.liferay.apio.consumer.extensions.mediumFormat
import com.liferay.apio.consumer.model.Thing

class PersonDetailCustom @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseView,
	LinearLayout(context, attrs, defStyleAttr) {

	override var screenlet: ThingScreenlet? = null

	val avatar by bindNonNull<ThingScreenlet>(R.id.person_avatar)
	val name by bindNonNull<TextView>(R.id.person_name)
	val email by bindNonNull<TextView>(R.id.person_email)
	val jobTitle by bindNonNull<TextView>(R.id.person_job_title)
	val birthDate by bindNonNull<TextView>(R.id.person_birthDate)

	override var thing: Thing? by converter<Person> {
		avatar.thing = thing
		name.text = it.name
		email.text = Html.fromHtml("<a href=\"mailto:${it.email}\">${it.email}</a>")
		email.linksClickable = true
		email.movementMethod = LinkMovementMethod.getInstance()
		jobTitle.text = it.jobTitle
		birthDate.text = it.birthDate?.mediumFormat()
	}
}
