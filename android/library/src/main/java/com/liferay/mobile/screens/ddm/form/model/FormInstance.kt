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

import com.liferay.apio.consumer.model.Relation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys
import com.liferay.mobile.screens.ddl.model.DDMStructure
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario
import java.util.*

/**
 * @author Paulo Cruz
 * @author Sarai Díaz García
 * @author Victor Oliveira
 */
data class FormInstance(
	val name: String,
	val description: String?,
	val defaultLanguage: String,
	val ddmStructure: DDMStructure,
	val hasDataProvider: Boolean,
	val hasFormRules: Boolean) {

	val isEvaluable: Boolean = hasDataProvider || hasFormRules

	companion object {
		val DEFAULT_VIEWS: MutableMap<Scenario, Int> =
			mutableMapOf(
				Detail to R.layout.ddm_form_default
			)

		val converter: (Thing) -> FormInstance = { it: Thing ->

			val name = it[FormConstants.NAME] as String

			val description = it[FormConstants.DESCRIPTION] as? String

			val defaultLanguage = it[FormConstants.DEFAULT_LANGUAGE] as String

			val locale = Locale(defaultLanguage)

			val ddmStructure = (it[FormConstants.STRUCTURE] as Relation).let {
				getStructure(it, locale)
			}

			val hasDataProvider = ddmStructure.fields.any {
				it.dataSourceType == FormConstants.DATA_PROVIDER_KEY || it.dataSourceType == FormConstants.FROM_AUTOFILL_KEY
			}

			val hasFormRules = ddmStructure.fields.any {
				it.hasFormRules()
			}

			FormInstance(name, description, defaultLanguage, ddmStructure, hasDataProvider, hasFormRules)
		}

		private fun getStructure(relation: Relation, locale: Locale): DDMStructure {

			val name = relation[FormConstants.NAME] as String

			val description = relation[FormConstants.DESCRIPTION] as? String

			val pages = (relation[FormConstants.PAGES] as Map<String, Any>).let {
				getPages(it, locale)
			}

			val successPage = relation[FormConstants.SUCCESS_PAGE]?.let {
				val successMap = it as Map<*, *>

				val headline = successMap[FormConstants.HEADLINE] as String
				val text = successMap[FormConstants.DESCRIPTION] as String

				SuccessPage(headline, text)
			}

			return DDMStructure(name, description, pages, successPage)
		}

		private fun getPages(mapper: Map<String, Any>, locale: Locale): List<FormPage> {

			return (mapper["member"] as List<Map<String, Any>>).mapTo(mutableListOf()) {

				val headlinePage = it[FormConstants.HEADLINE] as? String ?: ""
				val textPage = it[FormConstants.TEXT] as? String ?: ""
				val fields = (it[FormConstants.FIELDS] as Map<String, Any>).let {
					getFields(it, locale)
				}.toMutableList()

				FormPage(headlinePage, textPage, fields)
			}
		}

		private fun getFields(map: Map<String, Any>, locale: Locale): List<Field<*>> {

			if (map["totalItems"] as Double <= 0) {
				return mutableListOf()
			}

			return (map["member"] as List<Map<String, Any>>).mapTo(mutableListOf()) {

				val dataType = it[FormFieldKeys.DATA_TYPE_KEY] as? String
				val options = (it[FormFieldKeys.OPTIONS_KEY] as? Map<String, Any>)?.let {
					it["member"] as? List<Map<String, Any>>
				}

				val attributes = it.toMutableMap()
				attributes.remove(FormFieldKeys.OPTIONS_KEY)

				options?.let {
					attributes[FormFieldKeys.OPTIONS_KEY] = options
				}

				val fieldDataType = Field.DataType.assignDataTypeFromString(dataType)
				fieldDataType.createField(attributes, locale, locale)
			}
		}
	}
}
