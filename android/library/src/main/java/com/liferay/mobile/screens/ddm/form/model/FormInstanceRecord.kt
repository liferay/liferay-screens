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

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.ddl.model.Field
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * @author Paulo Cruz
 */
@SuppressLint("ParcelCreator")
class FormInstanceRecord(
	var id: String? = null,
	var fieldValues: List<FieldValue>) : Parcelable {

	constructor(parcel: Parcel) : this(
		parcel.readString(),
		mutableListOf()) {

		parcel.readList(fieldValues, FieldValue::class.java.classLoader)
	}

	companion object {
		@JvmStatic
		val converter: (Thing) -> FormInstanceRecord = { it: Thing ->

			val id = it.id

			val fieldValues = (it[FormConstants.FIELD_VALUES] as? Map<String, Any>)?.let {
				getFieldValues(it)
			} ?: emptyList()

			FormInstanceRecord(id, fieldValues)
		}

		private fun getFieldValues(map: Map<String, Any>): List<FieldValue> {
			if (map["totalItems"] as Double <= 0) {
				return mutableListOf()
			}

			return (map["member"] as List<Map<String, String>>).mapTo(mutableListOf()) {
				val name = it["name"] ?: ""
				val value = it["value"] ?: ""

				FieldValue(name, value)
			}
		}

		object CREATOR : Parcelable.Creator<FormInstanceRecord> {
			override fun createFromParcel(parcel: Parcel): FormInstanceRecord {
				return FormInstanceRecord(parcel)
			}

			override fun newArray(size: Int): Array<FormInstanceRecord?> {
				return arrayOfNulls(size)
			}
		}
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(id)
	}

	override fun describeContents(): Int {
		return 0
	}

}