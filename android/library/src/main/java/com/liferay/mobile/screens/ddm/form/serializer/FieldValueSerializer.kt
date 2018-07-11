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
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.ddm.form.model.Grid
import com.liferay.mobile.screens.ddm.form.model.RepeatableField
import java.lang.reflect.Type

/**
 * @author Paulo Cruz
 */
typealias FieldList = List<Field<*>>

class FieldValueSerializer {

    companion object {
        private val gson: Gson = GsonFactory.create()

        fun serialize(fields: FieldList): String {
            return fields
                    .flatten()
                    .removeEmptyOrTransient()
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

        private fun FieldList.removeEmptyOrTransient(): FieldList {
            return filter {
                !it.isTransient
            }.filter {
                it.currentValue?.let {
                    when(it) {
                        is Grid -> it.rawValues.isNotEmpty()
                        is List<*> -> it.isNotEmpty()
                        else -> it.toString().isNotEmpty()
                    }
                } ?: false
            }
        }

        private fun FieldList.mapValues(): List<Map<String, Any?>> {
            return map {
                mapOf("name" to it.name, "value" to it.getSubmitValue())
            }
        }

        private fun Field<*>.getSubmitValue(): Any? {
            return when (editorType) {
                Field.EditorType.RADIO -> (currentValue as? List<*>)?.get(0)
                else -> currentValue
            }
        }

        private fun List<Map<String, Any?>>.toJson(): String {
            return gson.toJson(this)
        }
    }
}
