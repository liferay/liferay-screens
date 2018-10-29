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

package com.liferay.mobile.screens.ddl

/**
 * @author Victor Galán Grande
 */
import android.util.Patterns

abstract class StringValidator(val errorMessage: String) {
	abstract fun validate(string: String): Boolean
}

class ContainsValidation(val value: String, errorMessage: String) : StringValidator(errorMessage) {
	override fun validate(string: String): Boolean = string.contains(value)
}

class NotContainsValidation(val value: String, errorMessage: String) : StringValidator(errorMessage) {
	override fun validate(string: String): Boolean = !string.contains(value)
}

class IsEmailValidation(errorMessage: String) : StringValidator(errorMessage) {
	override fun validate(string: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(string).matches()
}

class IsUrlValidation(errorMessage: String) : StringValidator(errorMessage) {
	override fun validate(string: String): Boolean = Patterns.WEB_URL.matcher(string).matches()
}

class RegexValidation(val regex: Regex, errorMessage: String) : StringValidator(errorMessage) {
	override fun validate(string: String): Boolean = regex.matches(string)
}

class DummyValidation : StringValidator("") {
	override fun validate(string: String): Boolean = true
}

class StringValidatorParser {

	fun parseStringValidation(validationMap: Map<String, String>): StringValidator {
		if (validationMap.isEmpty()) {
			return DummyValidation()
		}

		val errorMessage = validationMap["error"] ?: ""
		val expression = validationMap["expression"] ?: ""

		return when {
			expression.startsWith("isEmailAddress(") -> IsEmailValidation(errorMessage)
			expression.startsWith("isURL(") -> IsUrlValidation(errorMessage)
			expression.startsWith("contains(") ->
				executeRegexAndGetFirstResult("""contains\(\w+, "(\w*)"\)""", expression) {
					ContainsValidation(it, errorMessage)
				}
			expression.startsWith("NOT(contains(") ->
				executeRegexAndGetFirstResult("""NOT\(contains\(\w+, "(\w*)"\)\)""", expression) {
					NotContainsValidation(it, errorMessage)
				}
			expression.startsWith("match(") ->
				executeRegexAndGetFirstResult("""match\(\w+, "(\w*)"\)""", expression) {
					RegexValidation(it.toRegex(), errorMessage)
				}
			else -> DummyValidation()
		}
	}

	private fun executeRegexAndGetFirstResult(stringRegex: String, expression: String,
		and: (String) -> StringValidator): StringValidator {
		val result = stringRegex.toRegex().find(expression)
		return result?.groups?.get(1)?.value?.let(and) ?: DummyValidation()
	}
}
