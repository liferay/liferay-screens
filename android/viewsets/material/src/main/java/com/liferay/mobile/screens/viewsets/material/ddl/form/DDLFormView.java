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
	private static final Map<Field.EditorType, Integer> MATERIAL_LAYOUT_IDS = new HashMap<>();

	static {
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.CHECKBOX, R.layout.ddlfield_checkbox_material);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.DATE, R.layout.ddlfield_date_material);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.NUMBER, R.layout.ddlfield_number_default);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.INTEGER, R.layout.ddlfield_number_default);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.DECIMAL, R.layout.ddlfield_number_default);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.RADIO, R.layout.ddlfield_radio_default);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.SELECT, R.layout.ddlfield_select_material);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.CHECKBOX_MULTIPLE, R.layout.ddlfield_select_material);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.TEXT, R.layout.ddlfield_text_default);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.TEXT_AREA, R.layout.ddlfield_text_area_default);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.DOCUMENT, R.layout.ddlfield_document_material);
		MATERIAL_LAYOUT_IDS.put(Field.EditorType.GEO, R.layout.ddlfield_geo_default);

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
		setFieldLayoutId(editorType, MATERIAL_LAYOUT_IDS.get(editorType));
	}
}
