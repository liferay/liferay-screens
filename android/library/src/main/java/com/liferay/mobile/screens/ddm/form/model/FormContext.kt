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

import com.liferay.mobile.screens.ddl.JsonParser
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author Paulo Cruz
 */
class FormContext @JvmOverloads constructor(
	val isReadOnly: Boolean = false,
	val showRequiredFieldsWarning: Boolean = false,
	val showSubmitButton: Boolean = false,
	val pages: List<FormContextPage>) {

	companion object {
		val converter: (JSONObject) -> FormContext = { it: JSONObject ->

			val isReadOnly = it.getBoolean("isReadOnly")
			val showRequiredFieldsWarning = it.getBoolean("isShowRequiredFieldsWarning")
			val showSubmitButton = it.getBoolean("isShowSubmitButton")

			val pages = getPages(it.getJSONArray("pages"))

			FormContext(isReadOnly, showRequiredFieldsWarning, showSubmitButton, pages)
		}

		private fun getPages(jsonArrayPages: JSONArray): List<FormContextPage> {

			val formContextPageList = mutableListOf<FormContextPage>()

			for (i in 0 until jsonArrayPages.length()) {
				val jsonPage = jsonArrayPages.getJSONObject(i)

				val headline = jsonPage.optString("headline")
				val text = jsonPage.optString("text")

				val fields = getFields(jsonPage.getJSONArray("fields"))

				val isEnabled = jsonPage.getBoolean("isEnabled")
				val showRequiredFieldsWarning = jsonPage.getBoolean("isShowRequiredFieldsWarning")

				formContextPageList.add(FormContextPage(headline, text, fields, isEnabled, showRequiredFieldsWarning))
			}

			return formContextPageList
		}

		private fun getFields(jsonArray: JSONArray): List<FieldContext> {

			val fieldList = mutableListOf<FieldContext>()

			for (i in 0 until jsonArray.length()) {
				val jsonField = jsonArray.getJSONObject(i)

				val name = jsonField.getString("name")
				val value = jsonField.opt("value")
				val errorMessage = jsonField.optString("errorMessage")

				val options = if (jsonField.has("options")) {
					JsonParser.getJSONArrayAttributes(jsonField.getJSONArray("options"))
				} else {
					emptyList<Map<String, Any?>>()
				}

				val isEvaluable = jsonField.getBoolean("isEvaluable")
				val isReadOnly = jsonField.getBoolean("isReadOnly")
				val isRequired = jsonField.getBoolean("isRequired")
				val isValid = jsonField.getBoolean("isValid")
				val isValueChanged = jsonField.getBoolean("isValueChanged")
				val isVisible = jsonField.getBoolean("isVisible")

				fieldList.add(FieldContext(name, value, errorMessage, options as List<Map<String, Any>>?, isEvaluable,
					isReadOnly, isRequired, isValid, isValueChanged, isVisible))
			}

			return fieldList
		}
	}
}

class FormContextPage(val headline: String, val text: String, val fields: List<FieldContext>,
	val isEnabled: Boolean = false,
	val showRequiredFieldsWarning: Boolean = false)

class FieldContext(name: String, value: Any?, val errorMessage: String?,
	val options: List<Map<String, Any>>?, val isEvaluable: Boolean?,
	val isReadOnly: Boolean?, val isRequired: Boolean?, val isValid: Boolean?,
	val isValueChanged: Boolean?, val isVisible: Boolean?) : FieldValue(name, value)
