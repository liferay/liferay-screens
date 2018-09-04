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

package com.liferay.mobile.screens.ddl.model

/**
 * @author Victor Oliveira
 */
abstract class FormFieldKeys {
    val dataSourceTypeKey = "dataSourceType"
    val dataTypeKey = "dataType"
    val ddmDataProviderInstanceKey = "ddmDataProviderInstance"
    val displayStyleKey = "displayStyle"
    val labelKey = "label"
    val gridKey = "grid"
    val hasFormRulesKey = "hasFormRules"
    val isTransientKey = "isTransient"
    val nameKey = "name"
    val optionsKey = "options"
    val placeHolderKey = "placeHolder"
    val predefinedValueKey = "predefinedValue"
    val switcherKey = "switcher"
    val textKey = "text"
    val tipKey = "tip"
    val validationKey = "validation"
    val visibilityExpressionKey = "visibilityExpression"
    open val additionalTypeKey = "type"
    open val isInlineKey = "inline"
    open val isMultipleKey = "multiple"
    open val isReadOnlyKey = "readOnly"
    open val isRepeatableKey = "repeatable"
    open val isRequiredKey = "required"
    open val isShowLabelKey = "showLabel"
}

class DDLFormFieldKeys : FormFieldKeys()

class DDMFormFieldsKeys : FormFieldKeys() {
    override val additionalTypeKey = "additionalType"
}