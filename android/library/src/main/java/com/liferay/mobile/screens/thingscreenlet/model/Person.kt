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

package com.liferay.mobile.screens.thingscreenlet.model

import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.thingscreenlet.screens.views.Custom
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import com.liferay.apio.consumer.extensions.asDate
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get
import java.util.*

data class Person(
    val name: String?,
    val email: String?,
    val jobTitle: String?,
    val birthDate: Date?,
    val image: String?) {

    companion object {
        val DEFAULT_VIEWS: MutableMap<Scenario, Int> =
            mutableMapOf(
                Detail to R.layout.person_detail_default,
                Custom("portrait") to R.layout.person_portrait_default
            )

        val converter: (Thing) -> Any = { it: Thing ->

            val name = it["name"] as? String

            val email = it["email"] as? String

            val jobTitle = it["jobTitle"] as? String

            val birthDate = (it["birthDate"] as? String)?.asDate()

            val image = it["image"] as? String

            Person(name, email, jobTitle, birthDate, image)
        }
    }
}
