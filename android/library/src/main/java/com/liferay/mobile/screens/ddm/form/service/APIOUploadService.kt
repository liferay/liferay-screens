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
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile
import com.liferay.mobile.screens.util.AndroidUtil
import java.io.InputStream

/**
 * @author Paulo Cruz
 */
class APIOUploadService : BaseAPIOService() {

	fun uploadFile(formThing: Thing, field: DocumentField, inputStream: InputStream,
		onSuccess: (DocumentRemoteFile) -> Unit,
		onError: (Exception) -> Unit) {

		formThing.getOperation("upload-file")?.let { operation ->
			uploadFile(formThing.id, operation.id, field, inputStream, onSuccess, onError)
		}
	}

	fun uploadFile(thingId: String, operationId: String, field: DocumentField, inputStream: InputStream,
		onSuccess: (DocumentRemoteFile) -> Unit,
		onError: (Exception) -> Unit) {

		val filePath = field.currentValue?.toString()

		filePath?.also {
			val fileName = AndroidUtil.getFileNameFromPath(filePath)

			apioConsumer.performOperation(thingId, operationId, fillFields = { _ ->
				mapOf(
					Pair("binaryFile", inputStream),
					Pair("name", fileName),
					Pair("title", fileName)
				)
			}, onComplete = { result ->
				inputStream.close()

				result.fold({ thing ->
					onSuccess(DocumentRemoteFile(thing.id, fileName))
				}, onError)
			})
		}
	}
}