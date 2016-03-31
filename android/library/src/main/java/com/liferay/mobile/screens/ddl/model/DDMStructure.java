package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.ddl.JsonParser;
import com.liferay.mobile.screens.ddl.Parser;
import com.liferay.mobile.screens.ddl.XSDParser;
import com.liferay.mobile.screens.util.LiferayLocale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

	public DDMStructure(Map<String, Object> values, Locale locale) {
		_values = values;
		_locale = locale;
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

	public void parseXsd(String xsd) {
		parse(xsd, new XSDParser());
	}

	public void parseJson(String json) {
		parse(json, new JsonParser());
	}

	public Field getField(int index) {
		return _fields.get(index);
	}

	public int getFieldCount() {
		return _fields.size();
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

	private void parse(String content, Parser parser) {
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
	protected Map<String, Object> _values = new HashMap<>();
}