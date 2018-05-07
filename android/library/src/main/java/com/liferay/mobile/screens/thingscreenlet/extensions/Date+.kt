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

package com.liferay.mobile.screens.thingscreenlet.extensions

import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun Date.shortFormat(locale: Locale = Locale.US) = format(DateFormat.getDateInstance(DateFormat.SHORT, locale))

fun Date.mediumFormat(locale: Locale = Locale.US) = format(DateFormat.getDateInstance(DateFormat.MEDIUM, locale))

fun Date.longFormat(locale: Locale = Locale.US) = format(DateFormat.getDateInstance(DateFormat.LONG, locale))

fun Date.fullFormat(locale: Locale = Locale.US) = format(DateFormat.getDateInstance(DateFormat.FULL, locale))

fun Date.format(format: DateFormat): String = format.format(this)
