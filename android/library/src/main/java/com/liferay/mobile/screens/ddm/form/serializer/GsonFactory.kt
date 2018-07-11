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

package com.liferay.mobile.screens.ddm.form.serializer

import com.google.gson.*
import com.liferay.mobile.screens.ddl.model.Option
import com.liferay.mobile.screens.ddm.form.model.Grid
import java.lang.reflect.Type

/**
 * @author Paulo Cruz
 */
interface GsonFactory {

    companion object {
        fun create(): Gson {
            return GsonBuilder()
                    .registerTypeAdapter(Option::class.java, OptionSerializer())
                    .registerTypeAdapter(Grid::class.java, GridSerializer())
                    .setDateFormat("yyyy-MM-dd")
                    .create()
        }
    }

    private class OptionSerializer : JsonSerializer<Option> {

        override fun serialize(option: Option, type: Type,
                               context: JsonSerializationContext): JsonElement {

            return context.serialize(option.value)
        }
    }

    private class GridSerializer : JsonSerializer<Grid> {

        override fun serialize(grid: Grid, type: Type,
                               context: JsonSerializationContext): JsonElement {

            return context.serialize(grid.rawValues)
        }
    }

}