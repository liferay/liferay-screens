/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddm.form.serializer

import com.liferay.mobile.screens.ddl.model.*
import com.liferay.mobile.screens.ddm.form.model.Grid
import com.liferay.mobile.screens.ddm.form.model.RepeatableField
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Paulo Cruz
 */
class FieldValueSerializer {

	companion object {

		private const val EMPTY_STRING = ""
		private val EMPTY_LIST = listOf<Any>()

		fun serialize(fields: FieldList, filter: (Field<*>) -> (Boolean) = { true }): JSONArray {
			return fields
				.flatten()
				.filter(filter)
				.mapValues()
		}

		private fun FieldList.flatten(): FieldList {
			return flatMap {
				when (it) {
					is RepeatableField -> it.repeatedFields
					else -> listOf(it)
				}
			}
		}

		private fun Field<*>.getSubmitValue(): Any? {
			return when (editorType) {
				Field.EditorType.CHECKBOX_MULTIPLE,
				Field.EditorType.SELECT -> getListValue()
				Field.EditorType.DOCUMENT -> JSONObject(getDocumentValue())
				Field.EditorType.RADIO -> getRadioValue()
				else -> getStringValue()
			}
		}

		private fun Field<*>.getDocumentValue(): String {
			return (currentValue as? DocumentRemoteFile)?.toData() ?: ""
		}

		private fun Field<*>.getListValue(): List<*>? {
			return (currentValue as? List<*>) ?: EMPTY_LIST
		}

		private fun Field<*>.getRadioValue(): Any? {
			return (currentValue as? List<*>)?.let {
				if (it.isNotEmpty()) {
					it[0]
				} else {
					EMPTY_STRING
				}
			} ?: EMPTY_STRING
		}

		private fun Field<*>.getStringValue(): Any? {
			return currentValue ?: EMPTY_STRING
		}

		private fun FieldList.mapValues(): JSONArray {
			val jsonArray = JSONArray()

			this.forEach {
				val json = JSONObject()

				json.put("name", it.name)
				json.put("value", getSerializedValue(it.getSubmitValue()))

				jsonArray.put(json)
			}

			return jsonArray
		}

		private fun getGridValues(map: MutableMap<String, String>) : String {
			val json = JSONObject()

			map.forEach { (key, value) ->
				json.put(key, value)
			}

			return json.toString()
		}

		private fun getOptionList(list: List<*>): String {
			val array = JSONArray()
			list.forEach {
				if (it is Option) {
					array.put(it.value)
				}
			}

			return array.toString()
		}

		private fun getSerializedValue(value: Any?): String {
			return when (value) {
				is Option -> value.value
				is Grid -> getGridValues(value.rawValues)
				is Date -> SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(value)
				is List<*> -> getOptionList(value)
				else -> value.toString()
			}
		}
	}
}

typealias FieldList = List<Field<*>>
