package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.ddl.JsonParser;
import com.liferay.mobile.screens.ddl.Parser;
import com.liferay.mobile.screens.ddl.XSDParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class TemplatedAsset extends AssetEntry implements Parcelable {

	public static final ClassLoaderCreator<TemplatedAsset> CREATOR =
		new ClassLoaderCreator<TemplatedAsset>() {

			@Override
			public TemplatedAsset createFromParcel(Parcel source, ClassLoader loader) {
				return new TemplatedAsset(source, loader);
			}

			public TemplatedAsset createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public TemplatedAsset[] newArray(int size) {
				return new TemplatedAsset[size];
			}
		};

	public TemplatedAsset(Map<String, Object> values, Locale locale) {
		super(values);
		_locale = locale;
	}

	protected TemplatedAsset(Parcel in, ClassLoader loader) {
		super(in, loader);

		_locale = (Locale) in.readSerializable();
		Parcelable[] array = in.readParcelableArray(getClass().getClassLoader());
		_fields = new ArrayList(Arrays.asList(array));
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

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);

		dest.writeSerializable(_locale);
		dest.writeParcelableArray(_fields.toArray(new Field[_fields.size()]), flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	private void parse(String xsd, Parser parser) {
		try {
			_fields = parser.parse(xsd, _locale);
		}
		catch (Exception e) {
			_fields = new ArrayList<>();
		}
	}

	protected List<Field> _fields = new ArrayList<>();
	private Locale _locale;
}