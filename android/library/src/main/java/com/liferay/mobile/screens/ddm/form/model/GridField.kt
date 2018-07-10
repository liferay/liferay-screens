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
import com.liferay.mobile.screens.ddl.model.FormFieldKeys
import com.liferay.mobile.screens.ddl.model.Option
import java.util.*

/**
 * @author Victor Oliveira
 */
class GridField : Field<Grid>, Parcelable {
    lateinit var rows: List<Option>
    lateinit var columns: List<Option>

    override fun convertToData(value: Grid?): String {
        return value?.rawValues.toString()
    }

    override fun convertToFormattedString(value: Grid?): String {
        return value?.rawValues.toString()
    }

    constructor(parcel: Parcel, classLoader: ClassLoader) : super(parcel, classLoader) {
        parcel.createTypedArrayList(Option.CREATOR)
        parcel.createTypedArrayList(Option.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)

        parcel.writeTypedList(rows)
        parcel.writeTypedList(columns)
    }

    override fun doValidate(): Boolean {
        return currentValue.rawValues.size == this.rows.size
    }

    constructor(attributes: Map<String, Any>, locale: Locale, defaultLocale: Locale) : super(attributes, locale,
        defaultLocale) {

        val grid = attributes[FormFieldKeys.GRID] as Map<String, Any>
        rows = (grid["rows"] as Map<String, Any>).let {
            (it["member"] as List<Map<String, String>>).mapTo(mutableListOf(), {
                Option(it)
            })
        }

        columns = (grid["columns"] as Map<String, Any>).let {
            (it["member"] as List<Map<String, String>>).mapTo(mutableListOf(), {
                Option(it)
            })
        }
    }

    override fun convertFromString(stringValue: String): Grid? {
        if (!stringValue.isEmpty()) {
            val str = stringValue.substring(1, stringValue.length - 1)
            val keyValuePairs = str.split(",")
            val map = mutableMapOf<String, String>()

            keyValuePairs
                .map { pair ->
                    pair.split("=".toRegex())
                }
                .forEach { entry ->
                    map[entry[0].trim()] = entry[1].trim()
                }

            return Grid(map)
        }

        return null
    }

    override fun describeContents(): Int {
        return 0
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



