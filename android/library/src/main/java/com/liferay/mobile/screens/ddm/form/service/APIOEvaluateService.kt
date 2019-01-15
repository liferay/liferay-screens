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

import com.liferay.apio.consumer.exception.ThingWithoutOperationException
import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.serializer.FieldValueSerializer

/**
 * @author Paulo Cruz
 */
class APIOEvaluateService : BaseAPIOService() {

	private val operationId = "evaluate-context"

	fun evaluateContext(formThing: Thing, fields: MutableList<Field<*>>, onSuccess: (Thing) -> Unit,
		onError: (Exception) -> Unit) {

		formThing.getOperation(operationId)?.let { operation ->
			performEvaluate(formThing.id, operation.id, fields, onSuccess, onError)
		} ?: onError(ThingWithoutOperationException(formThing.id, operationId))
	}

	private fun performEvaluate(thingId: String, operationId: String, fields: MutableList<Field<*>>,
		onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

		apioConsumer.performOperation(thingId, operationId, fillFields = {
			mapOf(
				Pair(FormConstants.FIELD_VALUES, FieldValueSerializer.serialize(fields))
			)
		}) { result ->
			result.fold(onSuccess, onError)
		}
	}
}