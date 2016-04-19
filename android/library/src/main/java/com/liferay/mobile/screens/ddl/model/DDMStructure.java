package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.ddl.FieldParser;
import com.liferay.mobile.screens.ddl.JsonParser;
import com.liferay.mobile.screens.ddl.XSDParser;
import com.liferay.mobile.screens.util.LiferayLocale;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class DDMStructure implements Parcelable {

	public static final Creator<DDMStructure> CREATOR = new Creator<DDMStructure>() {
		@Override
		public DDMStructure createFromParcel(Parcel in) {
			return new DDMStructure(in);
		}

		@Override
		public DDMStructure[] newArray(int size) {
			return new DDMStructure[size];
		}
	};

	public DDMStructure(Locale locale) {
		_locale = locale;
		_parsed = false;
	}

	protected DDMStructure(Parcel in) {
		Field[] array = (Field[]) in.readParcelableArray(getClass().getClassLoader());
		_fields = new ArrayList<>(Arrays.asList(array));
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(_fields);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public Field getField(int index) {
		return _fields.get(index);
	}

	public int getFieldCount() {
		return _fields.size();
	}

	public boolean isParsed() {
		return _parsed;
	}

	public Field getFieldByName(String fieldName) {
		if (fieldName == null) {
			return null;
		}

		for (Field f : _fields) {
			if (fieldName.equals(f.getName())) {
				return f;
			}
		}

		return null;
	}

	public List<Field> getFields() {
		return _fields;
	}

	public void setFields(List<Field> fields) {
		_fields = fields;
	}

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void parse(JSONObject jsonObject) throws JSONException {
		if (jsonObject.has("xsd")) {
			parse(jsonObject.getString("xsd"), new XSDParser());
		}
		else {
			parse(jsonObject.getString("definition"), new JsonParser());
		}
		_parsed = true;
	}

	protected void parse(String content, FieldParser parser) {
		try {
			Locale locale = _locale == null ? LiferayLocale.getDefaultLocale() : _locale;
			_fields = parser.parse(content, locale);
		}
		catch (Exception e) {
			_fields = new ArrayList<>();
		}
	}

	protected List<Field> _fields = new ArrayList<>();
	protected Locale _locale = Locale.US;
	protected boolean _parsed;
}