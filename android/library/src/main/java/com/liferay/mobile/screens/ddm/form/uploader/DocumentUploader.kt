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

package com.liferay.mobile.screens.ddm.form.uploader

import android.net.Uri
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liferay.apio.consumer.ApioException
import com.liferay.apio.consumer.model.Operation
import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.getOperation
import com.liferay.apio.consumer.performOperation
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.DDMFormView
import okhttp3.Response

/**
 * @author Paulo Cruz
 */
fun DDMFormView.uploadFileToRootFolder(
        thing: Thing, field: DocumentField,
        onComplete: (Result<DocumentRemoteFile, Exception>) -> Unit) {

    val operation = thing.getOperation("upload-file-to-root-folder")

    uploadFile(thing, operation!!, field) {
        val (response, exception) = it

        exception?.let {
            onComplete(Result.error(it))
        } ?:
        response?.let {
            val typeToken =
                    TypeToken.getParameterized(
                            Map::class.java, String::class.java, Any::class.java).type

            val json = Gson().fromJson<Map<String, Any>>(response.body()?.string(), typeToken)

            json?.let {
                if(response.isSuccessful) {
                    val fileUrl = json["@id"] as String

                    onComplete(Result.of { DocumentRemoteFile(fileUrl) })
                }
                else {
                    val error = json["title"] as? String ?: "Unable to upload file"
                    onComplete(Result.error(ApioException(error)))
                }
            }
        }
    }
}

private fun DDMFormView.uploadFile(
        thing: Thing, operation: Operation, field: DocumentField,
        onComplete: (Result<Response, Exception>) -> Unit) {

    val filePath = field.currentValue?.toString()

    filePath?.let {
        val fileUri = Uri.parse(filePath)
        val fileName = fileUri.lastPathSegment
        val inputStream = context.contentResolver.openInputStream(fileUri)

        performOperation(thing.id, operation.id, {
            mapOf(
                Pair("binaryFile", inputStream),
                Pair("name", fileName),
                Pair("title", fileName)
            )
        }) {
            inputStream.close()

            onComplete(it)
        }
    }
}