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

package com.liferay.mobile.screens.ddm.form.model

import android.os.Parcel
import android.os.Parcelable
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys
import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.util.JSONUtil
import com.liferay.mobile.screens.util.extensions.toJSON
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Victor Oliveira
 */
class GridField : Field<Grid>, Parcelable {
	lateinit var rows: List<Option>
	lateinit var columns: List<Option>

	internal constructor() : super()

	constructor(attributes: Map<String, Any>, locale: Locale, defaultLocale: Locale) :
		super(attributes, locale, defaultLocale) {

		val grid = attributes[FormFieldKeys.GRID_KEY] as Map<String, Any>
		rows = (grid["rows"] as List<Map<String, String>>).mapTo(mutableListOf()) {
			Option(it)
		}

		columns = (grid["columns"] as List<Map<String, String>>).mapTo(mutableListOf()) {
			Option(it)
		}
	}

	constructor(parcel: Parcel, classLoader: ClassLoader) : super(parcel, classLoader) {
		rows = parcel.createTypedArrayList(Option.CREATOR) ?: ArrayList()
		columns = parcel.createTypedArrayList(Option.CREATOR) ?: ArrayList()
	}

	override fun convertToData(value: Grid?): String {
		return value?.rawValues.let {
			it?.toJSON()
		}.toString()
	}

	override fun convertToFormattedString(value: Grid?): String {
		return value?.rawValues.let {
			it?.toJSON()
		}.toString()
	}

	override fun doValidate(): Boolean {
		val rowsCompleted = currentValue?.rawValues?.let {
			it.size == this.rows.size
		} ?: true

		return !isRequired || rowsCompleted
	}

	override fun convertFromString(stringValue: String): Grid? {
		if (!stringValue.isEmpty()) {
			val str = stringValue.substring(1, stringValue.length - 1)

			if (!str.isEmpty()) {
				val keyValuePairs = str.split(",")
				val map = mutableMapOf<String, String>()

				keyValuePairs
					.map { pair ->
						pair.replace("\"", "").split(":".toRegex())
					}
					.forEach { entry ->
						map[entry[0].trim()] = entry[1].trim()
					}

				return Grid(map)
			}
		}

		return null
	}

	override fun describeContents(): Int {
		return 0
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		super.writeToParcel(parcel, flags)

		parcel.writeTypedList(rows)
		parcel.writeTypedList(columns)
	}

	companion object CREATOR : Parcelable.ClassLoaderCreator<GridField> {
		override fun createFromParcel(source: Parcel?): GridField {
			throw AssertionError()
		}

		override fun createFromParcel(parcel: Parcel, classLoader: ClassLoader): GridField {
			return GridField(parcel, classLoader)
		}

		override fun newArray(size: Int): Array<GridField?> {
			return arrayOfNulls(size)
		}
	}
}

operator fun List<Option>.get(value: String): Option? {
	return this.firstOrNull { it.value == value }
}
