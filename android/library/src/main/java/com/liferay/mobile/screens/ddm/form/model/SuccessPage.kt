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

/**
 * @author Victor Oliveira
 */
class SuccessPage(val headline: String, val text: String) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString())

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(headline)
		parcel.writeString(text)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<SuccessPage> {
		override fun createFromParcel(parcel: Parcel): SuccessPage {
			return SuccessPage(parcel)
		}

		override fun newArray(size: Int): Array<SuccessPage?> {
			return arrayOfNulls(size)
		}
	}
}