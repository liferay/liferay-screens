package com.liferay.mobile.screens.ddm.form

import com.google.gson.*
import com.liferay.mobile.screens.ddl.model.Option
import java.lang.reflect.Type

/**
 * @author Paulo Cruz
 */
class OptionSerializer : JsonSerializer<Option> {
    override fun serialize(option: Option, type: Type,
        context: JsonSerializationContext): JsonElement
            = JsonPrimitive(option.value)
}