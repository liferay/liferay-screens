package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.assetlist.AssetEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class WebContent extends AssetEntry implements WithDDM, Parcelable {

	public static final ClassLoaderCreator<WebContent> CREATOR =
		new ClassLoaderCreator<WebContent>() {
			@Override
			public WebContent createFromParcel(Parcel source, ClassLoader loader) {
				return new WebContent(source, loader);
			}

			@Override
			public WebContent createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			@Override
			public WebContent[] newArray(int size) {
				return new WebContent[size];
			}
		};

	public WebContent(Parcel in, ClassLoader classLoader) {
		super(in, classLoader);
	}

	public WebContent(Map<String, Object> map, Locale locale) {
		super(map);
		_ddmStructure = new DDMStructure(map, locale);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public DDMStructure getDDMStructure() {
		return _ddmStructure;
	}

	@Override
	public void parseDDMStructure(JSONObject jsonObject) throws JSONException {
		_ddmStructure.parse(jsonObject);
	}

	public String getContent() {
		//FIXME
		return (String) _values.get("content");
	}

	private DDMStructure _ddmStructure;
}
