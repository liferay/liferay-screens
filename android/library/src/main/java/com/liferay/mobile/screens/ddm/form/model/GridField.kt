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

package com.liferay.mobile.screens.ddm.form.model

import com.liferay.mobile.screens.ddl.model.Option


/**
 * @author Victor Oliveira
 */
class GridField(val columns: List<GridColumn>, val row: List<GridRow>)

class GridRow(label: String, options: List<Option>, value: String) : GridMember(label, options, value)

class GridColumn(label: String, options: List<Option>, value: String) : GridMember(label, options, value)

abstract class GridMember(val label: String, val options: List<Option>, val value: String)

