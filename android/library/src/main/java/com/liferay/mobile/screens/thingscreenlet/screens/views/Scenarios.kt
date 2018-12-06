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

package com.liferay.mobile.screens.thingscreenlet.screens.views

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

interface Scenario : Parcelable{
	companion object {
		var stringToScenario: ((String) -> Scenario?)? = null
	}
}

@SuppressLint("ParcelCreator")
object Detail : Scenario {
	override fun writeToParcel(dest: Parcel?, flags: Int) {}

    override fun describeContents() = 0

	object CREATOR : Parcelable.Creator<Detail> {
		override fun createFromParcel(parcel: Parcel): Detail {
			return Detail
		}

		override fun newArray(size: Int): Array<Detail?> {
			return arrayOfNulls(size)
		}
	}
}

@SuppressLint("ParcelCreator")
object Row : Scenario {
	override fun writeToParcel(dest: Parcel?, flags: Int) {
	}

	override fun describeContents() = 0

	object CREATOR : Parcelable.Creator<Row> {
		override fun createFromParcel(parcel: Parcel): Row {
			return Row
		}

		override fun newArray(size: Int): Array<Row?> {
			return arrayOfNulls(size)
		}
	}
}

data class Custom(val name: String) : Scenario {
	constructor(parcel: Parcel) : this(parcel.readString())

	override fun writeToParcel(dest: Parcel?, flags: Int) {
		dest?.writeString(name)
	}

    override fun describeContents() = 0

	companion object CREATOR : Parcelable.Creator<Custom> {
		override fun createFromParcel(parcel: Parcel): Custom {
			return Custom(parcel)
		}

		override fun newArray(size: Int): Array<Custom?> {
			return arrayOfNulls(size)
		}
	}
}
