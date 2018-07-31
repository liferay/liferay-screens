/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddm.form.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.parse
import okhttp3.Response

/**
 * @author Paulo Cruz
 */
fun Response.toJsonMap(): Map<String, Any>? {
    val mapType = TypeToken.getParameterized(Map::class.java, String::class.java, Any::class.java).type

    val body = this.body()?.string()

    return body?.let {
        Gson().fromJson<Map<String, Any>>(body, mapType)
    }
}

fun Response.toThing(): Thing? {
    val body = this.body()?.string()

    return body?.let {
        parse(body)?.let {
            val (thing, _) = it
            return thing
        }
    }
}