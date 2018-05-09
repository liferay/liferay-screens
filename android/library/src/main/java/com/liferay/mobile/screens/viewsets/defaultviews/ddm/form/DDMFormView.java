package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.ddl.model.DDMStructure;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.StringField;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.events.Event;
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView;
import com.liferay.mobile.sdk.apio.model.Thing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Paulo Cruz
 */
public class DDMFormView extends ScrollView implements BaseView {

	private static final Map<Field.EditorType, Integer> DEFAULT_LAYOUT_IDS = new HashMap<>(1);

	static {
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.CHECKBOX, R.layout.ddlfield_checkbox_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.CHECKBOX_MULTIPLE, R.layout.ddmfield_checkbox_multiple_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.DATE, R.layout.ddlfield_date_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.NUMBER, R.layout.ddlfield_number_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.INTEGER, R.layout.ddlfield_number_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.DECIMAL, R.layout.ddlfield_number_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.RADIO, R.layout.ddlfield_radio_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.SELECT, R.layout.ddlfield_select_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.TEXT, R.layout.ddlfield_text_default);
		DEFAULT_LAYOUT_IDS.put(Field.EditorType.TEXT_AREA, R.layout.ddlfield_text_area_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.DOCUMENT, R.layout.ddlfield_document_default);
		//DEFAULT_LAYOUT_IDS.put(Field.EditorType.GEO, R.layout.ddlfield_geo_default);
	}

	private final Map<Field.EditorType, Integer> layoutIds = new HashMap<>();
	private final Map<String, Integer> customLayoutIds = new HashMap<>();
	protected ViewGroup fieldsContainerView;
	private ThingScreenlet thingScreenlet;
	private Thing thing;

	public DDMFormView(Context context) {
		super(context);
	}

	public DDMFormView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DDMFormView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	//protected void addFieldView(Field field) {
	//    boolean containsKey = customLayoutIds.containsKey(field.getName());
	//    int layoutId = containsKey ? getCustomFieldLayoutId(field.getName()) : getFieldLayoutId(field.getEditorType());
	//
	//    int layoutId = this.getFieldLayoutId(field.getEditorType());
	//
	//    View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
	//    DDLFieldViewModel viewModel = (DDLFieldViewModel) view;
	//
	//    viewModel.setField(field);
	//    viewModel.setParentView(this);
	//
	//    fieldsContainerView.addView(view);
	//}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		fieldsContainerView = findViewById(R.id.ddmfields_container);
	}

	private FormInstance mockFormInstance() throws JSONException {

		Map<String, Object> attributes = new HashMap<>();
		attributes.put("dataType", Field.DataType.STRING);
		attributes.put("readOnly", false);
		attributes.put("type", Field.EditorType.TEXT);
		attributes.put("required", false);
		attributes.put("showLabel", true);
		attributes.put("repeatable", false);
		attributes.put("label", "TextField single");
		attributes.put("name", "TextFieldSingle");
		attributes.put("tip", "TextField hint");
		attributes.put("placeHolder", "");
		StringField stringField = new StringField(attributes, Locale.ENGLISH, Locale.ENGLISH);

		Map<String, Object> attributes2 = new HashMap<>();
		attributes.put("dataType", Field.DataType.STRING);
		attributes.put("readOnly", false);
		attributes.put("type", Field.EditorType.TEXT_AREA);
		attributes.put("required", false);
		attributes.put("showLabel", true);
		attributes.put("repeatable", false);
		attributes.put("label", "TextField multiple");
		attributes.put("name", "TextFieldMultiple");
		attributes.put("tip", "TextField multiple hint");
		attributes.put("placeHolder", "");
		StringField stringField2 = new StringField(attributes2, Locale.ENGLISH, Locale.ENGLISH);

		List<Field> fields = new ArrayList<>();
		fields.add(stringField);
		fields.add(stringField2);

		DDMStructure ddmStructure = new DDMStructure();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("descriptionCurrentValue", "Basic fields form");
		jsonObject.put("structureId", "36579");
		jsonObject.put("nameCurrentValue", "Basic fields");

		ddmStructure.parse(jsonObject);
		ddmStructure.setFields(fields);

		return new FormInstance(36582, ddmStructure, false, false, null);
	}

	@Nullable
	@Override
	public ThingScreenlet getScreenlet() {
		return thingScreenlet;
	}

	@Override
	public void setScreenlet(@Nullable ThingScreenlet thingScreenlet) {
		this.thingScreenlet = thingScreenlet;
	}

	@Nullable
	@Override
	public Thing getThing() {
		return thing;
	}

	@Override
	public void setThing(@Nullable Thing thing) {
		this.thing = thing;
	}

	@NotNull
	@Override
	public String getLoggerTag() {
		return null;
	}

	@Nullable
	@Override
	public <T> T sendEvent(@NotNull Event<T> event) {
		return null;
	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void showError(@Nullable String message) {

	}
}
