package com.liferay.mobile.screens.viewsets.westeros.ddl.form;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.viewsets.westeros.R;

import java.util.HashMap;
import java.util.Map;

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
		setFieldLayoutId(editorType, _westerosLayoutIds.get(editorType));
	}

	private static Map<Field.EditorType, Integer> _westerosLayoutIds = new HashMap<>();

	static {
		_westerosLayoutIds.put(Field.EditorType.CHECKBOX, R.layout.ddlfield_checkbox_westeros);
		_westerosLayoutIds.put(Field.EditorType.DATE, R.layout.ddlfield_date_westeros);
		_westerosLayoutIds.put(Field.EditorType.NUMBER, R.layout.ddlfield_number_westeros);
		_westerosLayoutIds.put(Field.EditorType.INTEGER, R.layout.ddlfield_number_westeros);
		_westerosLayoutIds.put(Field.EditorType.DECIMAL, R.layout.ddlfield_number_westeros);
		_westerosLayoutIds.put(Field.EditorType.RADIO, R.layout.ddlfield_radio_westeros);
		_westerosLayoutIds.put(Field.EditorType.SELECT, R.layout.ddlfield_select_westeros);
		_westerosLayoutIds.put(Field.EditorType.TEXT, R.layout.ddlfield_text_westeros);
		_westerosLayoutIds.put(Field.EditorType.TEXT_AREA, R.layout.ddlfield_text_area_westeros);
		_westerosLayoutIds.put(Field.EditorType.DOCUMENT, R.layout.ddlfield_document_westeros);
	}
}