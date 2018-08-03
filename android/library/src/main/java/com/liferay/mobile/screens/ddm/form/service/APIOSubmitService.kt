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

package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.ApioException
import com.liferay.apio.consumer.fetch
import com.liferay.apio.consumer.model.Operation
import com.liferay.apio.consumer.model.Relation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.apio.consumer.performParseOperation
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.serializer.FieldValueSerializer
import okhttp3.HttpUrl

/**
 * @author Paulo Cruz
 */
class APIOSubmitService : ISubmitService {

    fun submit(formThing: Thing, currentRecordThing: Thing?, fields: MutableList<Field<*>>,
               isDraft: Boolean = false, onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

        val recordsRelation = formThing.attributes["formInstanceRecords"] as? Relation

        currentRecordThing?.let {
            submit(it.id, "update", fields, isDraft, onSuccess, onError)
        } ?: recordsRelation?.let {
            submit(it.id, "create", fields, isDraft, onSuccess, onError)
        }
    }

    override fun submit(thingId: String, operationId: String,
                        fields: MutableList<Field<*>>, isDraft: Boolean,
                        onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

        getThing(thingId, { thing ->
            thing.getOperation(operationId)?.let { operation ->
                performSubmit(thing, operation, fields, isDraft, onSuccess, onError)
            }
        }, onError)
    }

    private fun getThing(thingId: String, onSuccess: (Thing) -> Unit,
                         onError: (Exception) -> Unit) {

        HttpUrl.parse(thingId)?.let {
            fetch(it) {
                val (thing, exception) = it

                thing?.let {
                    onSuccess(it)
                } ?: exception?.let{
                    onError(it)
                }
            }
        } ?: onError(ApioException("No thing found"))
    }

    private fun performSubmit(thing: Thing, operation: Operation, fields: MutableList<Field<*>>,
                              isDraft: Boolean = false, onSuccess: (Thing) -> Unit,
                              onError: (Exception) -> Unit) {

        performParseOperation(thing.id, operation.id, {
            mapOf(
                Pair("isDraft", isDraft),
                Pair("fieldValues", FieldValueSerializer.serialize(fields))
            )
        }) {
            val (resultThing, exception) = it

            resultThing?.let(onSuccess) ?: exception?.let(onError)
        }
    }
}
