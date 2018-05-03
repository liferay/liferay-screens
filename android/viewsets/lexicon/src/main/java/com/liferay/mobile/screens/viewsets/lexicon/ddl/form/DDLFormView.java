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

package com.liferay.mobile.screens.viewsets.lexicon.ddl.form;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.viewsets.lexicon.R;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Victor Oliveira
 */
public class DDLFormView extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormView {
	private static final Map<Field.EditorType, Integer> LEXICON_LAYOUT_IDS = new HashMap<>();

	static {
		LEXICON_LAYOUT_IDS.put(Field.EditorType.CHECKBOX, R.layout.ddlfield_checkbox_default);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.DATE, R.layout.ddlfield_date_lexicon);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.NUMBER, R.layout.ddlfield_number_default);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.INTEGER, R.layout.ddlfield_number_default);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.DECIMAL, R.layout.ddlfield_number_default);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.RADIO, R.layout.ddlfield_radio_lexicon);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.SELECT, R.layout.ddlfield_select_lexicon);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.CHECKBOX_MULTIPLE, R.layout.ddlfield_select_lexicon);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.TEXT, R.layout.ddlfield_text_default);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.TEXT_AREA, R.layout.ddlfield_text_area_default);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.PARAGRAPH, R.layout.ddlfield_text_area_default);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.DOCUMENT, R.layout.ddlfield_document_lexicon);
		LEXICON_LAYOUT_IDS.put(Field.EditorType.GEO, R.layout.ddlfield_geo_default);

	}

	public DDLFormView(Context context) {
		super(context);
	}

	public DDLFormView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public DDLFormView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void resetFieldLayoutId(Field.EditorType editorType) {
		setFieldLayoutId(editorType, LEXICON_LAYOUT_IDS.get(editorType));
	}
}
