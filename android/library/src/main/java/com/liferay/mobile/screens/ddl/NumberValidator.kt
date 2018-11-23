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

import java.text.NumberFormat
import java.util.regex.Pattern

/**
 * @author Victor Oliveira
 */
abstract class NumberValidator(val right: Number, override val errorMessage: String) : Validator {
	abstract fun validate(left: Number): Boolean

	companion object {

		private val PATTERN: Pattern = Pattern.compile("[^0-9]+([0-9]+)$")

		@JvmStatic
		fun parseNumberValidator(validationMap: Map<String, Any>, fieldName: String): NumberValidator {
			val errorMessage = validationMap["errorMessage"] as? String ?: ""
			val expression = validationMap["expression"] as? String ?: ""

			val matcher = PATTERN.matcher(expression)
			if (matcher.find() && matcher.groupCount() >= 1) {
				val numberStr = matcher.group(1)
				val operator = matcher.group(0).removeSuffix(numberStr).removePrefix(fieldName)
				val number = NumberFormat.getInstance().parse(numberStr)

				return when (operator) {
					">=" -> IsGreaterThanOrEqualTo(number, errorMessage)
					">" -> IsGreaterThan(number, errorMessage)
					"==" -> IsEqualTo(number, errorMessage)
					"<=" -> IsLessThanOrEqualTo(number, errorMessage)
					"<" -> IsLessThan(number, errorMessage)
					else -> Unknown(NumberFormat.getInstance().parse("0"), "Unknown Error")
				}
			}

			return Unknown(NumberFormat.getInstance().parse("0"), "Unknown Error")
		}
	}

	class IsGreaterThanOrEqualTo(right: Number, errorMessage: String) : NumberValidator(right, errorMessage) {
		override fun validate(left: Number): Boolean = left.toDouble() >= right.toDouble()
	}

	class IsGreaterThan(right: Number, errorMessage: String) : NumberValidator(right, errorMessage) {
		override fun validate(left: Number): Boolean = left.toDouble() > right.toDouble()
	}

	class IsEqualTo(right: Number, errorMessage: String) : NumberValidator(right, errorMessage) {
		override fun validate(left: Number): Boolean = left.toDouble() == right.toDouble()
	}

	class IsLessThanOrEqualTo(right: Number, errorMessage: String) : NumberValidator(right, errorMessage) {
		override fun validate(left: Number): Boolean = left.toDouble() <= right.toDouble()
	}

	class IsLessThan(right: Number, errorMessage: String) : NumberValidator(right, errorMessage) {
		override fun validate(left: Number): Boolean = left.toDouble() < right.toDouble()
	}

	class Unknown(number: Number, errorMessage: String) : NumberValidator(number, errorMessage) {
		override fun validate(left: Number): Boolean = true
	}
}
