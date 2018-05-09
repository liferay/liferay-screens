package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.ddl.DDMStructureParser;
import com.liferay.mobile.screens.ddl.JsonParser;
import com.liferay.mobile.screens.ddl.XSDParser;
import com.liferay.mobile.screens.ddm.form.model.SuccessPage;
import com.liferay.mobile.screens.util.LiferayLocale;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDMStructure implements Parcelable {

	public static final Parcelable.ClassLoaderCreator<DDMStructure> CREATOR = new ClassLoaderCreator<DDMStructure>() {
		@Override
		public DDMStructure createFromParcel(Parcel source, ClassLoader classLoader) {
			return new DDMStructure(source, classLoader);
		}

		@Override
		public DDMStructure createFromParcel(Parcel in) {
			throw new AssertionError();
		}

		@Override
		public DDMStructure[] newArray(int size) {
			return new DDMStructure[size];
		}
	};

	protected List<Field> fields = new ArrayList<>();
	protected Locale locale = Locale.US;
	protected boolean parsed;

	private String description;
	private String name;
	private String structureKey;
	private String structureId;
	private Long classNameId;
	private String classPK;
	private SuccessPage successPage;

	public DDMStructure() {
		super();
	}

	public DDMStructure(Locale locale) {
		this.locale = locale;
		parsed = false;
	}

	protected DDMStructure(Parcel in, ClassLoader classLoader) {
		Parcelable[] array = in.readParcelableArray(Field.class.getClassLoader());
		fields = new ArrayList(Arrays.asList(array));
		locale = (Locale) in.readSerializable();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelableArray(fields.toArray(new Field[fields.size()]), flags);
		dest.writeSerializable(locale);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public Field getField(int index) {
		return fields.get(index);
	}

	public int getFieldCount() {
		return fields.size();
	}

	public boolean isParsed() {
		return parsed;
	}

	public Field getFieldByName(String fieldName) {
		if (fieldName == null) {
			return null;
		}

		for (Field f : fields) {
			if (fieldName.equals(f.getName())) {
				return f;
			}
		}

		return null;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public SuccessPage getSuccessPage() {
		return successPage;
	}

	public void parse(JSONObject jsonObject) throws JSONException {
		description = parseString(jsonObject, "descriptionCurrentValue");
		name = parseString(jsonObject, "nameCurrentValue");
		structureKey = parseString(jsonObject, "structureKey");
		structureId = parseString(jsonObject, "structureId");

		classNameId = jsonObject.has("classNameId") ? jsonObject.getLong("classNameId") : 0;
		classPK = "com.liferay.dynamic.data.mapping.model.DDMStructure";

		if (jsonObject.has("xsd")) {
			parse(jsonObject.getString("xsd"), new XSDParser());
		} else {
			parse(jsonObject.getString("definition"), new JsonParser());
		}
		parsed = true;
	}

	private String parseString(JSONObject jsonObject, String key) throws JSONException {
		return jsonObject.has(key) ? jsonObject.getString(key) : "";
	}

	protected void parse(String content, DDMStructureParser parser) {
		try {
			Locale locale = this.locale == null ? LiferayLocale.getDefaultLocale() : this.locale;
			fields = parser.parse(content, locale);
		} catch (Exception e) {
			fields = new ArrayList<>();
		}
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getStructureKey() {
		return structureKey;
	}

	public String getStructureId() {
		return structureId;
	}

	public Long getClassNameId() {
		return classNameId;
	}

	public String getClassPK() {
		return classPK;
	}
}
