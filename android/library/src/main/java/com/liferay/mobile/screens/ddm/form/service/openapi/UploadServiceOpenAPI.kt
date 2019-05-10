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
import com.liferay.mobile.screens.ddm.form.model.FormInstance
import com.liferay.mobile.screens.ddm.form.service.BaseService
import com.liferay.mobile.screens.ddm.form.service.UploadService
import com.liferay.mobile.screens.util.AndroidUtil
import com.liferay.mobile.screens.util.JSONUtil
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.MultipartBuilder
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import rx.Observable
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URLConnection

/**
 * @author Victor Oliveira
 */
class UploadServiceOpenAPI(serverUrl: String) : UploadService, BaseService<DocumentRemoteFile>(serverUrl) {
	override fun uploadFile(formInstance: FormInstance, field: DocumentField, inputStream: InputStream)
		: Observable<DocumentRemoteFile> {

		val filePath = field.currentValue?.toString()

		return filePath?.let {
			val fileName = AndroidUtil.getFileNameFromPath(filePath)
			val multipartBuilder = MultipartBuilder().type(MultipartBuilder.FORM)

			val byteArray = getByteArrayFromInputStream(inputStream)
			val contentType = getContentType(filePath, inputStream)
			val body = RequestBody.create(MediaType.parse(contentType), byteArray)

			val mediaForm = JSONObject().put("title", fileName)

			multipartBuilder.addFormDataPart("file", fileName, body)
			multipartBuilder.addFormDataPart("formDocument", mediaForm.toString())

			val url = "${getBaseUrl()}/forms/${formInstance.id}/form-document"

			return execute(url, POST, multipartBuilder.build()) {
				response -> DocumentRemoteFile(response.body().string())
			}

		} ?: Observable.empty()
	}

	private fun getByteArrayFromInputStream(inputStream: InputStream): ByteArray {
		val byteBuffer = ByteArrayOutputStream()

		inputStream.use { input ->
			byteBuffer.use { output ->
				input.copyTo(output)
			}
		}

		return byteBuffer.toByteArray()
	}

	private fun getContentType(name: String, inputStream: InputStream): String {
		return URLConnection.guessContentTypeFromStream(inputStream)
			?: URLConnection.guessContentTypeFromName(name)
			?: "application/*"
	}
}