package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.assetlist.AssetEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
		_html = (String) map.get("content");
	}

	public WebContent(String html) {
		super(new HashMap<String, Object>());
		_html = html;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeParcelable(_ddmStructure, flags);
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

	public String getHtml() {
		return _html;
	}

	private DDMStructure _ddmStructure;
	private String _html;
}
