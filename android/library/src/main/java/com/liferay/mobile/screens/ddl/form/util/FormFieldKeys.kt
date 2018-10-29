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

package com.liferay.mobile.screens.ddl.form.util

/**
 * @author Victor Oliveira
 */
class FormFieldKeys {

	companion object {
		const val DATA_SOURCE_TYPE_KEY = "dataSourceType"
		const val DATA_TYPE_KEY = "dataType"
		const val DDM_DATA_PROVIDER_INSTANCE_KEY = "ddmDataProviderInstance"
		const val DISPLAY_STYLE_KEY = "displayStyle"
		const val LABEL_KEY = "label"
		const val GRID_KEY = "grid"
		const val HAS_FORM_RULES_KEY = "hasFormRules"
		const val IS_TRANSIENT_KEY = "transient"
		const val NAME_KEY = "name"
		const val OPTIONS_KEY = "options"
		const val PLACE_HOLDER_KEY = "placeHolder"
		const val PREDEFINED_VALUE_KEY = "predefinedValue"
		const val SWITCHER_KEY = "showAsSwitcher"
		const val TEXT_KEY = "text"
		@JvmField
		val TIP_KEY = arrayOf("tooltip", "tip")
		const val VALIDATION_KEY = "validation"
		const val VISIBILITY_EXPRESSION_KEY = "visibilityExpression"
		@JvmField
		val ADDITIONAL_TYPE_KEY = arrayOf("additionalType", "type")
		const val IS_INLINE_KEY = "inline"
		const val IS_MULTIPLE_KEY = "multiple"
		const val IS_READ_ONLY_KEY = "readOnly"
		const val IS_REPEATABLE_KEY = "repeatable"
		const val IS_REQUIRED_KEY = "required"
		const val IS_SHOW_LABEL_KEY = "showLabel"

		@JvmStatic
		fun getValueFromArrayKey(attributes: Map<String, Any>, arrayKey: Array<String>): Any? {
			for (additionalType in arrayKey) {
				val mapValue = attributes[additionalType]

				if (mapValue != null) {
					return mapValue
				}
			}

			return null
		}
	}
}
