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

package com.liferay.mobile.screens.thingscreenlet.screens

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario

/**
 * @author Marcelo Mello
 */
class ThingScreenletSavedState : View.BaseSavedState {

    lateinit var thing: Thing
    lateinit var scenario: Scenario

	constructor(superState: Parcelable) : super(superState)

	internal constructor(parcel: Parcel) : super(parcel) {
		thing = parcel.readParcelable(Thing::class.java.classLoader)
	}

	override fun writeToParcel(out: Parcel, flags: Int) {
		super.writeToParcel(out, flags)
		out.writeParcelable(thing, flags)
	}

	object CREATOR : Parcelable.Creator<ThingScreenletSavedState> {
		override fun createFromParcel(parcel: Parcel): ThingScreenletSavedState {
			return ThingScreenletSavedState(parcel)
		}

		override fun newArray(size: Int): Array<ThingScreenletSavedState?> {
			return arrayOfNulls(size)
		}
	}

	override fun describeContents(): Int = 0
}