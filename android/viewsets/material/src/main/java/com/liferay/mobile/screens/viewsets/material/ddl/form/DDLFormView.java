package com.liferay.mobile.screens.viewsets.material.ddl.form;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.viewsets.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class DDLFormView extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.DDLFormView {
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
		setFieldLayoutId(editorType, _materialLayoutIds.get(editorType));
	}

	private static Map<Field.EditorType, Integer> _materialLayoutIds = new HashMap<>();

	static {
		_materialLayoutIds.put(Field.EditorType.CHECKBOX, R.layout.ddlfield_checkbox_material);
		_materialLayoutIds.put(Field.EditorType.DATE, R.layout.ddlfield_date_material);
		_materialLayoutIds.put(Field.EditorType.NUMBER, R.layout.ddlfield_number_default);
		_materialLayoutIds.put(Field.EditorType.INTEGER, R.layout.ddlfield_number_default);
		_materialLayoutIds.put(Field.EditorType.DECIMAL, R.layout.ddlfield_number_default);
		_materialLayoutIds.put(Field.EditorType.RADIO, R.layout.ddlfield_radio_default);
		_materialLayoutIds.put(Field.EditorType.SELECT, R.layout.ddlfield_select_material);
		_materialLayoutIds.put(Field.EditorType.TEXT, R.layout.ddlfield_text_default);
		_materialLayoutIds.put(Field.EditorType.TEXT_AREA, R.layout.ddlfield_text_area_default);
		_materialLayoutIds.put(Field.EditorType.DOCUMENT, R.layout.ddlfield_document_material);
	}
}
