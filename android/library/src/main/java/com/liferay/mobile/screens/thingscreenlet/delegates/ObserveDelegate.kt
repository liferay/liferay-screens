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

package com.liferay.mobile.screens.thingscreenlet.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> observeNonNull(onChange: (T) -> Unit): ReadWriteProperty<Any, T?> = ObserveNonNullDelegate(onChange)

fun <T> observe(onChange: (T?) -> Unit): ReadWriteProperty<Any, T?> = ObserveDelegate(onChange)

@PublishedApi
internal class ObserveNonNullDelegate<T>(val onChange: (T) -> Unit) : ReadWriteProperty<Any, T?> {

	private var t: T? = null

	override fun getValue(thisRef: Any, property: KProperty<*>): T? = t

	override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
		t = value

		value?.apply(onChange)
	}
}

@PublishedApi
internal class ObserveDelegate<T>(val onChange: (T?) -> Unit) : ReadWriteProperty<Any, T?> {

	private var t: T? = null

	override fun getValue(thisRef: Any, property: KProperty<*>): T? = t

	override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
		t = value

		value.apply(onChange)
	}
}
