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

package com.liferay.mobile.screens.ddm.form.service.openapi

import com.liferay.mobile.screens.ddl.form.util.FormConstants
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormContext
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.serializer.FieldValueSerializer
import com.liferay.mobile.screens.ddm.form.service.BaseService
import com.liferay.mobile.screens.ddm.form.service.EvaluateService
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import rx.Observable

/**
 * @author Victor Oliveira
 */
class EvaluateServiceOpenAPI(serverUrl : String) : BaseService<FormContext>(serverUrl), EvaluateService {

	override fun evaluateContext(formInstance: FormInstance, fields: MutableList<Field<*>>) : Observable<FormContext> {
		val url = "${getBaseUrl()}/forms/${formInstance.id}/evaluate-context"
			.plus("")

		val jsonBody = JSONObject()

		jsonBody.put(FormConstants.FIELD_VALUES, FieldValueSerializer.serialize(fields) { !it.isTransient })

		val requestBody = RequestBody.create(JSON_MEDIA_TYPE, jsonBody.toString())

		return execute(url, POST, requestBody) {
			val body = it.body().string()
			FormContext.converter(JSONObject(body))
		}
	}
}