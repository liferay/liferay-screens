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

/**
 * @author Victor Oliveira
 */
class FormPage(val headline: String, val description: String, var fields: List<Field<*>> = emptyList(),
	var isEnabled: Boolean = true) : Parcelable {

	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString()) {

		fields = (parcel.readParcelableArray(Field::class.java.classLoader) as? Array<Field<*>>)?.let {
			it.toList()
		} ?: emptyList()

		isEnabled = parcel.readByte() != 0.toByte()
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(headline)
		parcel.writeString(description)
		parcel.writeParcelableArray(fields.toTypedArray(), flags)
		parcel.writeByte(if (isEnabled) 1 else 0)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<FormPage> {
		override fun createFromParcel(parcel: Parcel): FormPage {
			return FormPage(parcel)
		}

		override fun newArray(size: Int): Array<FormPage?> {
			return arrayOfNulls(size)
		}
	}

}
