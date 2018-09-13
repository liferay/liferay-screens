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
 * @author Paulo Cruz
 */
open class FieldValue(val name: String, var value: Any?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readValue(String::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FieldValue> {
        override fun createFromParcel(parcel: Parcel): FieldValue {
            return FieldValue(parcel)
        }

        override fun newArray(size: Int): Array<FieldValue?> {
            return arrayOfNulls(size)
        }
    }
}

operator fun List<FieldValue>.get(name: String): FieldValue {
    return this.first {
        it.name == name
    }
}

operator fun List<FieldValue>.set(name: String, value: String) {
    this[name].value = value
}
