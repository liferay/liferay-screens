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

import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.ddl.form.util.FormConstants
import java.util.Locale

/**
 * @author Paulo Cruz
 */
class APIOGetFormService : BaseAPIOService(Locale.getDefault()) {

	fun getForm(formInstanceId: Long, serverUrl: String, onSuccess: (Thing) -> Unit,
		onError: (Throwable) -> Unit) {

		val formUrl = getFormUrl(formInstanceId, serverUrl)

		apioConsumer.fetchResource(formUrl) { result ->
			result.fold(onSuccess, onError)
		}
	}

	private fun getFormUrl(formInstanceId: Long, serverUrl: String): String {
		return serverUrl + String.format(FormConstants.URL_TEMPLATE, formInstanceId)
	}

}