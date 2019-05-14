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
package com.liferay.mobile.screens.ddm.form.service

import com.liferay.mobile.screens.context.LiferayServerContext
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormContext
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord
import com.liferay.mobile.screens.ddm.form.service.openapi.FormServiceOpenAPI
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.Response
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.InputStream
import java.util.concurrent.Callable

/**
 * @author Victor Oliveira
 */
abstract class BaseService<T>(private val serverUrl: String) {
	inline fun execute(url: String, method: String = GET, body: RequestBody? = null,
		crossinline transformFunction: (Response) -> T): Observable<T> {

		return rx.Observable.fromCallable {
			val request = Request.Builder()
				.url(url)
				.method(method, body)
				.build()

			LiferayServerContext.getOkHttpClient(true).newCall(request).execute()
		}.map {
			transformFunction(it)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
	}

	fun getBaseUrl(): String {
		val url = if (!serverUrl.endsWith('/')) "$serverUrl/" else serverUrl
		return url.plus("o/headless-form/v1.0")
	}

	companion object {
		val JSON_MEDIA_TYPE: MediaType = MediaType.parse("application/json; charset=utf-8")
		const val GET = "GET"
		const val POST = "POST"
		const val PUT = "PUT"
	}

}

interface FormService : EvaluateService, FetchLatestDraftService, GetFormService, SubmitService, UploadService

interface EvaluateService {
	fun evaluateContext(formInstance: FormInstance, fields: MutableList<Field<*>>): Observable<FormContext>
}

interface FetchLatestDraftService {
	fun fetchLatestDraft(formInstanceId: Long): Observable<FormInstanceRecord>
}

interface GetFormService {
	fun getForm(formInstanceId: Long): Observable<FormInstance>
}

interface SubmitService {
	fun submit(formInstance: FormInstance, formInstanceRecord: FormInstanceRecord?, fields: MutableList<Field<*>>,
		isDraft: Boolean = false): Observable<FormInstanceRecord>
}

interface UploadService {
	fun uploadFile(formInstance: FormInstance, field: DocumentField, inputStream: InputStream)
		: Observable<DocumentRemoteFile>
}