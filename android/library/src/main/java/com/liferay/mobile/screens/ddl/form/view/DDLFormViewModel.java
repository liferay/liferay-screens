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

import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import java.util.Map;
import rx.Observable;

/**
 * @author Silvio Santos
 */
public interface DDLFormViewModel extends BaseViewModel {

	/**
	 * The layout associated with each form field.
	 *
	 * @return a layout resource id associated with the field editor type
	 */
	int getFieldLayoutId(Field.EditorType editorType);

	/**
	 * Sets the layout associated a field
	 * You should use this method if you want to change the layout of your fields
	 *
	 * @param editorType EditorType associated with this layout
	 * @param layoutId the layout resource id for this editor type
	 */
	void setFieldLayoutId(Field.EditorType editorType, int layoutId);

	/**
	 * Sets the default layout associated a field
	 * You should use this method if you want to use the default/standard layout for this field
	 *
	 * @param editorType EditorType associated with this layout
	 */
	void resetFieldLayoutId(Field.EditorType editorType);

	/**
	 * The layout associated with one specific field.
	 *
	 * @return a layout resource id associated with specified field
	 */
	int getCustomFieldLayoutId(String fieldName);

	/**
	 * Sets the layout associated a specific field
	 * You should use this method if you want to change the layout of one specific field
	 *
	 * @param fieldName the name of the field to change its layout
	 * @param layoutId the layout resource id for the specified field
	 */
	void setCustomFieldLayoutId(String fieldName, int layoutId);

	/**
	 * Sets the default layout corresponding to fieldName's editor
	 * You should use this method if you want to use the default/standard layout
	 *
	 * @param fieldName the name of the field to change its layout
	 */
	void resetCustomFieldLayoutId(String fieldName);

	void showValidationResults(Map<Field, Boolean> fieldResults, boolean autoscroll);

	void showFormFields(Record record);

	void showStartOperation(String actionName, Object argument);

	void showFinishOperation(String actionName, Object argument);

	void showFailedOperation(String actionName, Exception e, Object argument);

	Observable<EventProperty> getEventsObservable();
}
