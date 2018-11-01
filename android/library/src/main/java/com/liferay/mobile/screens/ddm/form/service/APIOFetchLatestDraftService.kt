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
import com.liferay.apio.consumer.model.Operation
import com.liferay.apio.consumer.model.Thing

/**
 * @author Paulo Cruz
 */
class APIOFetchLatestDraftService : BaseAPIOService() {

	private val operationId = "fetch-latest-draft"

	fun fetchLatestDraft(formThing: Thing, onSuccess: (Thing) -> Unit,
		onError: (Exception) -> Unit) {

		formThing.getOperation(operationId)?.let {
			performFetch(formThing, it, onSuccess, onError)
		} ?: onError(ThingWithoutOperationException(formThing.id, operationId))
	}

	private fun performFetch(thing: Thing, operation: Operation, onSuccess: (Thing) -> Unit,
		onError: (Exception) -> Unit) {

		apioConsumer.performOperation(thing.id, operation.id, onComplete = { result ->
			result.fold(onSuccess, onError)
		})
	}

}