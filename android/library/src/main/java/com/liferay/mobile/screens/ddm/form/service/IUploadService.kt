package com.liferay.mobile.screens.ddm.form.service

import android.content.Context
import com.liferay.mobile.screens.ddl.model.DocumentField
import com.liferay.mobile.screens.ddl.model.DocumentRemoteFile

/**
 * @author Paulo Cruz
 */
interface IUploadService {

    fun uploadFile(context: Context, thingId: String, operationId: String, field: DocumentField,
                   onSuccess: (DocumentRemoteFile) -> Unit, onError: (Exception) -> Unit)

}