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

package com.liferay.mobile.screens.thingscreenlet.model

import com.liferay.mobile.screens.thingscreenlet.ApioException
import com.liferay.mobile.screens.thingscreenlet.graph

data class Thing(val id: String, val type: List<String>, val attributes: Map<String, Any>, val name: String? = null)

data class Relation(val id: String)

data class Context(val vocab: String, val attributeContext: Map<String, Any>)

fun contextFrom(jsonObject: List<Any>?, parentContext: Context?): Context? {
	return jsonObject?.let {
		val vocab =
			it.find { it is Map<*, *> && it["@vocab"] is String }
				.let { (it as? Map<*, *>)?.get("@vocab") as? String }
				?: parentContext?.vocab
				?: throw ApioException("Empty Vocab")

		val attributeContexts = HashMap<String, Any>()

		it
			.filter { it is Map<*, *> }
			.forEach({
				(it as? Map<String, Any>)?.filterKeys { it != "@vocab" }?.let {
					attributeContexts.putAll(it)
				}
			})

		Context(vocab, attributeContexts)
	}
}

fun Context.isId(attributeName: String): Boolean =
	(attributeContext[attributeName] as? Map<String, Any>)
		?.let { it["@type"] }
		?.let { it == "@id" }
		?: false

operator fun Thing.get(attribute: String): Any? = attributes[attribute]

operator fun Relation.get(attribute: String): Any? = graph[id]?.value?.get(attribute)

fun Thing.merge(value: Thing?): Thing = value?.let { Thing(id, type, attributes + it.attributes) } ?: this

