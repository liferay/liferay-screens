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

import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile
import com.liferay.mobile.screens.ddl.model.Field
import com.liferay.mobile.screens.ddm.form.service.APIOEvaluateService
import com.liferay.mobile.screens.ddm.form.service.APIOFetchLatestDraftService
import com.liferay.mobile.screens.ddm.form.service.APIOSubmitService
import com.liferay.mobile.screens.ddm.form.service.APIOUploadService
import java.io.InputStream

/**
 * @author Victor Oliveira
 */
class DDMFormInteractor {
	fun evaluateContext(formThing: Thing, fields: MutableList<Field<*>>,
		onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

		APIOEvaluateService().evaluateContext(formThing, fields, onSuccess, onError)
	}

	fun fetchLatestDraft(formThing: Thing, onSuccess: (Thing) -> Unit,
		onError: (Exception) -> Unit) {

		APIOFetchLatestDraftService().fetchLatestDraft(formThing, onSuccess, onError)
	}

	fun submit(formThing: Thing, currentRecordThing: Thing?, fields: MutableList<Field<*>>,
		isDraft: Boolean = false, onSuccess: (Thing) -> Unit, onError: (Exception) -> Unit) {

		APIOSubmitService().submit(formThing, currentRecordThing, fields, isDraft, onSuccess, onError)
	}

	fun uploadFile(formThing: Thing, field: DocumentField, inputStream: InputStream,
		onSuccess: (DocumentRemoteFile) -> Unit,
		onError: (Exception) -> Unit) {

		APIOUploadService().uploadFile(formThing, field, inputStream, onSuccess, onError)
	}
}