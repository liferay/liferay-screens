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

package com.liferay.mobile.screens.ddm.form.model;

import android.os.Parcel;
import com.liferay.mobile.screens.ddl.model.FormFieldKeys;
import com.liferay.mobile.screens.ddl.model.SelectableOptionsField;
import java.util.Locale;
import java.util.Map;

/**
 * @author Paulo Cruz
 */
public class CheckboxMultipleField extends SelectableOptionsField {

	private boolean isShowAsSwitcher;

	public CheckboxMultipleField() {
	}

	public CheckboxMultipleField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
		super(attributes, locale, defaultLocale);

		isShowAsSwitcher = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.SWITCHER));
	}

	public CheckboxMultipleField(Parcel in, ClassLoader loader) {
		super(in, loader);

		isShowAsSwitcher = in.readInt() == 1;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		super.writeToParcel(destination, flags);

		destination.writeInt(isShowAsSwitcher ? 1 : 0);
	}

	public boolean isShowAsSwitcher() {
		return isShowAsSwitcher;
	}
}
