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

import com.google.gson.*
import com.liferay.mobile.screens.ddl.model.*
import com.liferay.mobile.screens.ddm.form.model.RepeatableField

/**
 * @author Paulo Cruz
 */
class FieldValueSerializer {

    companion object {
        private val gson: Gson = GsonFactory.create()

        private val EMPTY_JSON = JsonObject()
        private const val EMPTY_STRING = ""
        private val EMPTY_LIST = listOf<Any>()

        fun serialize(fields: FieldList, filter: (Field<*>) -> (Boolean) = { true }): String {
            return fields
                    .flatten()
                    .filter(filter)
                    .mapValues()
                    .toJson()
        }

        private fun FieldList.flatten(): FieldList {
            return flatMap {
                when(it) {
                    is RepeatableField -> it.repeatedFields
                    else -> listOf(it)
                }
            }
        }

        private fun Field<*>.getSubmitValue(): Any? {
            return when (editorType) {
                Field.EditorType.CHECKBOX_MULTIPLE,
                Field.EditorType.SELECT -> getListValue()
                Field.EditorType.DOCUMENT -> getDocumentValue()
                Field.EditorType.RADIO -> getRadioValue()
                else -> getStringValue()
            }
        }

        private fun Field<*>.getDocumentValue(): Any? {
            return (currentValue as? DocumentRemoteFile)?.toData() ?: EMPTY_JSON
        }

        private fun Field<*>.getListValue(): List<*>? {
            return (currentValue as? List<*>) ?: EMPTY_LIST
        }

        private fun Field<*>.getRadioValue(): Any? {
            return (currentValue as? List<*>)?.let {
                if(it.isNotEmpty()) {
                    it[0]
                } else {
                    EMPTY_STRING
                }
            } ?: EMPTY_STRING
        }

        private fun Field<*>.getStringValue(): Any? {
            return currentValue ?: EMPTY_STRING
        }

        private fun FieldList.mapValues(): List<Map<String, Any?>> {
            return map {
                mapOf("name" to it.name, "value" to it.getSubmitValue())
            }
        }

        private fun List<Map<String, Any?>>.toJson(): String {
            return gson.toJson(this)
        }
    }
}

typealias FieldList = List<Field<*>>
