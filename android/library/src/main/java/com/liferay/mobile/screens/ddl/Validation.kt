package com.liferay.mobile.screens.ddl

import com.liferay.mobile.screens.ddl.model.Field

interface Validation {
	fun getErrorMessage(): String
	fun getValidationState(): Field.ValidationState
	fun getLocalValidator(): LocalValidator?
}

interface LocalValidator {
	fun getCustomErrorMessage(): String
}