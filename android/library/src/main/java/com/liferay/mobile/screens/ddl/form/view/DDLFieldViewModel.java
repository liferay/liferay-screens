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

package com.liferay.mobile.screens.ddl.form.view;

import android.view.View;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldRadioView;
import rx.Observable;

/**
 * @author Silvio Santos
 */
public interface DDLFieldViewModel<T extends Field> {

	/**
	 * Returns the DDL field.
	 */
	T getField();

	/**
	 * Sets a DDL field.
	 */
	void setField(T field);

	/**
	 * Call this method for refreshing the DDL field.
	 */
	void refresh();

	/**
	 * Called with the validation result.
	 * For example, see {@link DDLFieldRadioView#onPostValidation(boolean)}
	 */
	void onPostValidation(boolean valid);

	/**
	 * Gets the parent view.
	 */
	View getParentView();

	/**
	 * Sets the parent view.
	 */
	void setParentView(View view);

	Observable getObservable();
}