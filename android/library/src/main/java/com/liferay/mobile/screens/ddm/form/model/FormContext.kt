package com.liferay.mobile.screens.ddm.form.model

import com.liferay.apio.consumer.model.Thing
import com.liferay.apio.consumer.model.get

/**
 * @author Paulo Cruz
 */
class FormContext @JvmOverloads constructor(
	val isReadOnly: Boolean = false,
	val isShowRequiredFieldsWarning: Boolean = false,
	val isShowSubmitButton: Boolean = false,
	val pages: List<FormContextPage>) {

	companion object {
		val converter: (Thing) -> FormContext = { it: Thing ->

			val isReadOnly = it["isReadOnly"] as Boolean
			val isShowRequiredFieldsWarning = it["isShowRequiredFieldsWarning"] as Boolean
			val isShowSubmitButton = it["isShowSubmitButton"] as Boolean

			val pages = (it["pages"] as Map<String, Any>).let {
				getPages(it)
			}

			FormContext(isReadOnly, isShowRequiredFieldsWarning, isShowSubmitButton, pages)
		}

		private fun getPages(mapper: Map<String, Any>): List<FormContextPage> {

			return (mapper["member"] as List<Map<String, Any>>).mapTo(mutableListOf()) {

				val headline = it["headline"] as? String ?: ""
				val text = it["text"] as? String ?: ""

				val fields = (it["fields"] as Map<String, Any>).let {
					getFields(it)
				}

				val isEnabled = it["isEnabled"] as Boolean
				val isShowRequiredFieldsWarning = it["isShowRequiredFieldsWarning"] as Boolean

				FormContextPage(headline, text, fields, isEnabled, isShowRequiredFieldsWarning)
			}
		}

		private fun getFields(map: Map<String, Any>): List<FieldContext> {

			if (map["totalItems"] as Double <= 0) {
				return mutableListOf()
			}

			return (map["member"] as List<Map<String, Any>>).mapTo(mutableListOf()) {

				val name = it["name"] as String
				val value = it["value"]
				val errorMessage = it["errorMessage"] as? String

				val options = (it["options"] as? Map<String, Any>)?.let {
					it["member"] as? List<Map<String, Any>>
				}

				val isEvaluable = it["isEvaluable"] as? Boolean
				val isReadOnly = it["isReadOnly"] as? Boolean
				val isRequired = it["isRequired"] as? Boolean
				val isValid = it["isValid"] as? Boolean
				val isValueChanged = it["isValueChanged"] as? Boolean
				val isVisible = it["isVisible"] as? Boolean

				FieldContext(name, value, errorMessage, options, isEvaluable, isReadOnly,
					isRequired, isValid, isValueChanged, isVisible)
			}
		}
	}
}

class FormContextPage(val headline: String, val text: String, val fields: List<FieldContext>,
	val isEnabled: Boolean = false,
	val isShowRequiredFieldsWarning: Boolean = false)

class FieldContext(name: String, value: Any?, val errorMessage: String?,
	val options: List<Map<String, Any>>?, val isEvaluable: Boolean?,
	val isReadOnly: Boolean?, val isRequired: Boolean?, val isValid: Boolean?,
	val isValueChanged: Boolean?, val isVisible: Boolean?) : FieldValue(name, value)
