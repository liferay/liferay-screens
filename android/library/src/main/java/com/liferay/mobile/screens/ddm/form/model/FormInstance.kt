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

import android.os.Parcelable
import com.liferay.mobile.screens.ddl.JsonParser
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys
import com.liferay.mobile.screens.ddl.model.DDMStructure
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.util.extensions.getOptionalJSONObject
import com.liferay.mobile.screens.util.extensions.getOptionalString
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * @author Paulo Cruz
 * @author Sarai Díaz García
 * @author Victor Oliveira
 */
@Parcelize
data class FormInstance(
	val name: String,
	val description: String?,
	val defaultLanguage: String,
	val ddmStructure: DDMStructure,
	val hasDataProvider: Boolean,
	val hasFormRules: Boolean) : Parcelable {

	val isEvaluable: Boolean = hasDataProvider || hasFormRules

	companion object {
		val converter: (JSONObject) -> FormInstance = { it: JSONObject ->

			val name = it.getString(FormConstants.NAME)

			val description = it.getOptionalString(FormConstants.DESCRIPTION)

			val defaultLanguage = it.getString(FormConstants.DEFAULT_LANGUAGE)

			val locale = Locale(defaultLanguage)

			val ddmStructure = getStructure(it.getJSONObject(FormConstants.STRUCTURE), locale)

			val hasDataProvider = ddmStructure.fields.any {
				it.dataSourceType == FormConstants.DATA_PROVIDER_KEY || it.dataSourceType == FormConstants.FROM_AUTOFILL_KEY
			}

			val hasFormRules = ddmStructure.fields.any {
				it.hasFormRules()
			}

			FormInstance(name, description, defaultLanguage, ddmStructure, hasDataProvider, hasFormRules)
		}

		private fun getStructure(jsonRelation: JSONObject, locale: Locale): DDMStructure {

			val name = jsonRelation.getString(FormConstants.NAME)

			val description = jsonRelation.getOptionalString(FormConstants.DESCRIPTION)

			val pages = getPages(jsonRelation.getJSONArray(FormConstants.PAGES), locale)

			val successPage = jsonRelation.getOptionalJSONObject(FormConstants.SUCCESS_PAGE)?.let { jsonSuccessPage ->
				val headline = jsonSuccessPage.getString(FormConstants.HEADLINE)
				val text = jsonSuccessPage.getString(FormConstants.DESCRIPTION)

				SuccessPage(headline, text)
			}

			return DDMStructure(name, description, pages, successPage)
		}

		private fun getPages(jsonArrayPages: JSONArray, locale: Locale): List<FormPage> {

			val formPageList = mutableListOf<FormPage>()

			for (i in 0 until jsonArrayPages.length()) {
				val jsonPage = jsonArrayPages.getJSONObject(i)

				val headlinePage = jsonPage.getOptionalString(FormConstants.HEADLINE) ?: ""
				val textPage = jsonPage.getOptionalString(FormConstants.TEXT) ?: ""
				val fields = getFields(jsonPage.getJSONArray(FormConstants.FIELDS), locale)

				formPageList.add(FormPage(headlinePage, textPage, fields))
			}

			return formPageList
		}

		private fun getFields(jsonArrayFields: JSONArray, locale: Locale): List<Field<*>> {

			val fieldList = mutableListOf<Field<*>>()

			for (i in 0 until jsonArrayFields.length()) {
				val jsonField = jsonArrayFields.getJSONObject(i)

				val dataType = jsonField.getString(FormFieldKeys.DATA_TYPE_KEY)

				val fieldDataType = Field.DataType.assignDataTypeFromString(dataType)
				fieldList.add(fieldDataType.createField(JsonParser.getAttributes(jsonField), locale, locale))
			}

			return fieldList
		}
	}
}
