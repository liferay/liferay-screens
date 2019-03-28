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

import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormContext
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord
import com.liferay.mobile.screens.ddm.form.service.FormService
import rx.Observable
import java.io.InputStream

/**
 * @author Victor Oliveira
 */
class FormServiceOpenAPI : FormService {

	override fun evaluateContext(formInstance: FormInstance, fields: MutableList<Field<*>>,
		onSuccess: (formContext: FormContext) -> Unit, onError: (Throwable) -> Unit) {

		evaluateService.evaluateContext(formInstance, fields, onSuccess, onError)
	}

	override fun fetchLatestDraft(formInstance: FormInstance, onSuccess: (FormInstanceRecord) -> Unit,
		onError: (Throwable) -> Unit) {

		fetchLatestDraftService.fetchLatestDraft(formInstance, onSuccess, onError)
	}

	override fun getForm(formInstanceId: Long, serverUrl: String): Observable<FormInstance> {
		return getFormService.getForm(formInstanceId, serverUrl)
	}

	override fun submit(formInstance: FormInstance, currentRecordThing: FormInstanceRecord,
		fields: MutableList<Field<*>>, isDraft: Boolean, onSuccess: (FormInstanceRecord) -> Unit,
		onError: (Throwable) -> Unit) {

		submitService.submit(formInstance, currentRecordThing, fields, isDraft, onSuccess, onError)
	}

	override fun submit(formInstanceId: String, operationId: String, fields: MutableList<Field<*>>, isDraft: Boolean,
		onSuccess: (FormInstanceRecord) -> Unit, onError: (Throwable) -> Unit) {

		submitService.submit(formInstanceId, operationId, fields, isDraft, onSuccess, onError)
	}

	override fun uploadFile(formInstance: FormInstance, field: DocumentField, inputStream: InputStream,
		onSuccess: (DocumentRemoteFile) -> Unit, onError: (Throwable) -> Unit) {

		uploadService.uploadFile(formInstance, field, inputStream, onSuccess, onError)
	}

	private val getFormService by lazy { GetFormServiceOpenAPI() }
	private val evaluateService by lazy { EvaluateServiceOpenAPI() }
	private val fetchLatestDraftService by lazy { FetchLatestDraftServiceOpenAPI() }
	private val submitService by lazy { SubmitServiceOpenAPI() }
	private val uploadService by lazy { UploadServiceOpenAPI() }

	companion object {
		const val OPEN_API_FORM_PATH = "/o/headless-form/v1.0"
	}
}