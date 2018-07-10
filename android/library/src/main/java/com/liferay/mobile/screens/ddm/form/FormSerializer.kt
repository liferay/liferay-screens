package com.liferay.mobile.screens.ddm.form

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.ddm.form.model.Grid
import java.lang.reflect.Type

/**
 * @author Paulo Cruz
 */
class OptionSerializer : JsonSerializer<Option> {
    override fun serialize(option: Option, type: Type,
        context: JsonSerializationContext): JsonElement = JsonPrimitive(option.value)
}

class GridSerializer : JsonSerializer<Grid> {
    override fun serialize(grid: Grid, type: Type,
        context: JsonSerializationContext): JsonElement {
        return context.serialize(grid.rawValues)
    }
}
