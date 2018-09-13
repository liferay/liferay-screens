package com.liferay.mobile.screens.ddm.form.service

import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.apio.consumer.performParseOperation
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.serializer.FieldValueSerializer

/**
 * @author Paulo Cruz
 */
class APIOEvaluateService : IEvaluateService {

    fun evaluateContext(formThing: Thing, fields: MutableList<Field<*>>,
                        onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

        formThing.getOperation("evaluate-context")?.let { operation ->
            evaluateContext(formThing.id, operation.id, fields, onSuccess, onError)
        }
    }

    override fun evaluateContext(thingId: String, operationId: String,
                                 fields: MutableList<Field<*>>, onSuccess: (Thing) -> Unit,
                                 onError: (Exception) -> Unit) {

        performParseOperation(thingId, operationId, {
            mapOf(
                Pair("fieldValues", FieldValueSerializer.serialize(fields))
            )
        }) {
            val (resultThing, exception) = it

            exception?.let(onError) ?: resultThing?.let(onSuccess)
        }
    }
}