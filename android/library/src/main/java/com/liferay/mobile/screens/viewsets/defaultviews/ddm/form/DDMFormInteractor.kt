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

package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form

import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.model.FormContext
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord
import com.liferay.mobile.screens.ddm.form.service.openapi.*
import java.io.InputStream

/**
 * @author Victor Oliveira
 */
class DDMFormInteractor {
	private val formService by lazy { FormServiceOpenAPI() }

	fun getForm(formInstanceId: Long, serverUrl: String) {
		formService.getForm(formInstanceId, serverUrl)
	}

	fun evaluateContext(formInstance: FormInstance, fields: MutableList<Field<*>>,
		onSuccess: (FormContext) -> Unit, onError: (Throwable) -> Unit) {

		formService.evaluateContext(formInstance, fields, onSuccess, onError)
	}

	fun fetchLatestDraft(formInstance: FormInstance, onSuccess: (FormInstanceRecord) -> Unit,
		onError: (Throwable) -> Unit) {

		formService.fetchLatestDraft(formInstance, onSuccess, onError)
	}

	fun submit(formInstance: FormInstance, currentRecordThing: FormInstanceRecord?, fields: MutableList<Field<*>>,
		isDraft: Boolean = false, onSuccess: (FormInstanceRecord) -> Unit, onError: (Throwable) -> Unit) {

		currentRecordThing?.let {
			formService.submit(formInstance, currentRecordThing, fields, isDraft, onSuccess, onError)
		}
	}

	fun uploadFile(formInstance: FormInstance, field: DocumentField, inputStream: InputStream,
		onSuccess: (DocumentRemoteFile) -> Unit,
		onError: (Throwable) -> Unit) {

		formService.uploadFile(formInstance, field, inputStream, onSuccess, onError)
	}
}