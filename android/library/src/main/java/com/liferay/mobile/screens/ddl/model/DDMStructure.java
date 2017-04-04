package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.ddl.DDMStructureParser;
import com.liferay.mobile.screens.ddl.JsonParser;
import com.liferay.mobile.screens.ddl.XSDParser;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStructureKey() {
		return structureKey;
	}

	public void setStructureKey(String structureKey) {
		this.structureKey = structureKey;
	}

	public String getStructureId() {
		return structureId;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public Long getClassNameId() {
		return classNameId;
	}

	public void setClassNameId(Long classNameId) {
		this.classNameId = classNameId;
	}

	public String getClassPK() {
		return classPK;
	}

	public void setClassPK(String classPK) {
		this.classPK = classPK;
	}

	public void parse(JSONObject jsonObject) throws JSONException {
		this.description = jsonObject.getString("descriptionCurrentValue");
		this.name = jsonObject.getString("nameCurrentValue");
		this.structureKey = jsonObject.getString("structureKey");
		this.structureId = jsonObject.getString("structureId");
		this.classNameId = jsonObject.getLong("classNameId");
		this.classPK = "com.liferay.dynamic.data.mapping.model.DDMStructure";
		if (jsonObject.has("xsd")) {
			parse(jsonObject.getString("xsd"), new XSDParser());
		} else {
			parse(jsonObject.getString("definition"), new JsonParser());
		}
		parsed = true;
	}

	protected void parse(String content, DDMStructureParser parser) {
		try {
			Locale locale = this.locale == null ? LiferayLocale.getDefaultLocale() : this.locale;
			fields = parser.parse(content, locale);
		} catch (Exception e) {
			fields = new ArrayList<>();
		}
	}

}