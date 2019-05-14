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
class FormServiceOpenAPI(serverUrl: String) : FormService {

	override fun evaluateContext(formInstance: FormInstance, fields: MutableList<Field<*>>): Observable<FormContext> {

		return evaluateService.evaluateContext(formInstance, fields)
	}

	override fun fetchLatestDraft(formInstanceId: Long): Observable<FormInstanceRecord> {
		return fetchLatestDraftService.fetchLatestDraft(formInstanceId)
	}

	override fun getForm(formInstanceId: Long): Observable<FormInstance> {
		return getFormService.getForm(formInstanceId)
	}

	override fun submit(formInstance: FormInstance, formInstanceRecord: FormInstanceRecord?,
		fields: MutableList<Field<*>>, isDraft: Boolean): Observable<FormInstanceRecord> {

		return submitService.submit(formInstance, formInstanceRecord, fields, isDraft)
	}

	override fun uploadFile(formInstance: FormInstance, field: DocumentField, inputStream: InputStream)
		: Observable<DocumentRemoteFile> {

		return uploadService.uploadFile(formInstance, field, inputStream)
	}

	private val getFormService by lazy { GetFormServiceOpenAPI(serverUrl) }
	private val evaluateService by lazy { EvaluateServiceOpenAPI(serverUrl) }
	private val fetchLatestDraftService by lazy { FetchLatestDraftServiceOpenAPI(serverUrl) }
	private val submitService by lazy { SubmitServiceOpenAPI(serverUrl) }
	private val uploadService by lazy { UploadServiceOpenAPI(serverUrl) }
}