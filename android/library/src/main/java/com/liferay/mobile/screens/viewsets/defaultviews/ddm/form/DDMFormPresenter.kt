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

import com.liferay.apio.consumer.delegates.converter
import com.liferay.apio.consumer.model.Thing
import com.liferay.mobile.screens.ddl.form.view.DDLFieldViewModel
import com.liferay.mobile.screens.ddl.model.*
import com.liferay.mobile.screens.ddm.form.model.*
import com.liferay.mobile.screens.util.LiferayLogger
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.events.FormEvents
import java.io.InputStream
import java.util.*

/**
 * @author Victor Oliveira
 */
class DDMFormPresenter(val view: DDMFormViewContract.DDMFormView) : DDMFormViewContract.DDMFormViewPresenter {
	private val interactor = DDMFormInteractor()

	private val dirtyFieldNames: MutableList<String> = mutableListOf()
	private var isSyncing = false

	private var currentRecordThing: Thing? by converter<FormInstanceRecord> {
		formInstanceRecord = it
	}
	var formInstanceRecord: FormInstanceRecord? = null

	override fun addToDirtyFields(field: Field<*>) {
		if (!dirtyFieldNames.contains(field.name)) {
			dirtyFieldNames.add(field.name)
		}
	}

	override fun checkIsDirty(field: Field<*>, fieldContext: FieldContext, fieldViewModel: DDLFieldViewModel<*>?) {
		if (dirtyFieldNames.contains(field.name)) {
			val isValid = fieldContext.isValid ?: true

			field.lastValidationResult = isValid
			fieldViewModel?.onPostValidation(isValid)
		}
	}

	override fun evaluateContext(thing: Thing, fields: MutableList<Field<*>>, onComplete: (() -> Unit)?) {
		view.showModalEvaluateContextLoading()

		interactor.evaluateContext(thing, fields, {
			view.hideModalLoading()

			val formContext = FormContext.converter(it)
			view.updatePageEnabled(formContext)

			updateFields(formContext, fields)
			onComplete?.invoke()
		}, {
			LiferayLogger.e(it.message)

			view.hideModalLoading()
			view.showErrorMessage(it)

			onComplete?.invoke()
		})
	}

	override fun onFieldValueChanged(thing: Thing, formInstance: FormInstance, field: Field<*>) {
		if (!field.isTransient) {
			addToDirtyFields(field)

			formInstanceRecord?.let {
				it.fieldValues[field.name] = field.toData() ?: ""
			}

			if (!view.hasConnectivity()) {
				view.showOfflineWarningMessage()
				return
			}

			if (!isSyncing) {
				submit(thing, formInstance, true)

				if (field.hasFormRules()) {
					evaluateContext(thing, formInstance.ddmStructure.fields)
				}
			}
		}
	}

	override fun restore(formInstanceRecord: FormInstanceRecord?, fields: MutableList<Field<*>>) {
		formInstanceRecord?.let {
			this.formInstanceRecord = it
			updateFields(it.fieldValues, fields)
		}
	}

	override fun submit(thing: Thing, formInstance: FormInstance, isDraft: Boolean) {
		val fields = formInstance.ddmStructure.fields
		view.isSubmitEnabled(isDraft)

		interactor.submit(thing, currentRecordThing, fields, isDraft, { recordThing ->
			currentRecordThing = recordThing

			if (!isDraft) {
				formInstance.ddmStructure.successPage?.let {
					view.showSuccessPage(formInstance.ddmStructure.successPage)
				} ?: run {
					view.showSuccessMessage()
				}
				view.isSubmitEnabled(true)
				view.sendCustomEvent(FormEvents.SUBMIT_SUCCESS, thing)
			}
		}, { exception ->
			LiferayLogger.e(exception.message)

			if (!isDraft) {
				view.isSubmitEnabled(true)
				view.showErrorMessage(exception)
				view.sendCustomEvent(FormEvents.SUBMIT_FAILED, thing)
			}
		})
	}

	override fun syncFormInstance(thing: Thing, formInstance: FormInstance) {
		isSyncing = true
		view.showModalSyncFormLoading()

		val fields = formInstance.ddmStructure.fields

		if (formInstance.hasDataProvider) {
			fetchDataProviders(thing, fields) {
				fetchLatestDraft(thing, fields) {
					onCompleteFetch(thing, formInstance, fields)
				}
			}
		} else {
			fetchLatestDraft(thing, fields) {
				onCompleteFetch(thing, formInstance, fields)
			}
		}
	}

	override fun uploadFile(
		thing: Thing, field: DocumentField, inputStream: InputStream, onSuccess: (DocumentRemoteFile) -> Unit,
		onError: (Exception) -> Unit) {

		interactor.uploadFile(thing, field, inputStream, onSuccess, onError)
	}

	private fun fetchDataProviders(thing: Thing, fields: MutableList<Field<*>>, onComplete: (() -> Unit)?) {
		view.showModalEvaluateContextLoading()

		interactor.evaluateContext(thing, fields, {
			val formContext = FormContext.converter(it)
			view.updatePageEnabled(formContext)

			updateFields(formContext, fields)

			onComplete?.invoke()
		}, {
			LiferayLogger.e(it.message)

			view.hideModalLoading()
			view.showErrorMessage(it)

			onComplete?.invoke()
		})
	}

	private fun fetchLatestDraft(thing: Thing, fields: MutableList<Field<*>>, onComplete: (() -> Unit)?) {
		interactor.fetchLatestDraft(thing, {
			view.hideModalLoading()

			currentRecordThing = it

			formInstanceRecord?.let { formInstanceRecord ->
				updateFields(formInstanceRecord.fieldValues, fields)
			}

			onComplete?.invoke()
		}, {
			LiferayLogger.e(it.message)

			view.hideModalLoading()

			onComplete?.invoke()
		})
	}

	private fun onCompleteFetch(thing: Thing, formInstance: FormInstance, fields: MutableList<Field<*>>) {
		if (formInstance.isEvaluable) {
			evaluateContext(thing, fields) {
				isSyncing = false
			}
		} else {
			isSyncing = false
		}
	}

	private fun setOptions(fieldContext: FieldContext, optionsField: OptionsField<*>) {
		val availableOptions = fieldContext.options as? List<Map<String, String>>

		availableOptions?.let {
			optionsField.availableOptions = ArrayList(availableOptions.map { Option(it) })
		}
	}

	private fun setValue(fieldContext: FieldContext, field: Field<*>) {
		if (fieldContext.isValueChanged == true) {
			addToDirtyFields(field)

			fieldContext.value?.toString()?.let {
				field.setCurrentStringValue(it)
			}
		}
	}

	private fun updateFields(fieldValues: List<FieldValue>, fields: MutableList<Field<*>>) {
		val fieldsMap = fields.map {
			Pair(it.name, it)
		}.toMap()

		fieldValues.forEach { fieldValue ->
			val field = fieldsMap[fieldValue.name]

			field?.also {
				if (!field.isTransient) {
					field.setCurrentStringValue(fieldValue.value as String)
				}
			}
		}

		view.refreshVisibleFields()
	}

	private fun updateFields(formContext: FormContext, fields: MutableList<Field<*>>) {
		val fieldContexts = formContext.pages.flatMap(FormContextPage::fields)

		val fieldsMap = fields.map {
			Pair(it.name, it)
		}.toMap()

		fieldContexts.forEach { fieldContext ->
			val field = fieldsMap[fieldContext.name]

			field?.let {
				(field as? OptionsField<*>)?.let { optionsField ->
					setOptions(fieldContext, optionsField)
				}

				setValue(fieldContext, field)

				field.isReadOnly = fieldContext.isReadOnly ?: field.isReadOnly
				field.isRequired = fieldContext.isRequired ?: field.isRequired

				view.updateFieldView(fieldContext, field)
			}
		}
	}

}