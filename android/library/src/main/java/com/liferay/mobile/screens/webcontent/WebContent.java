package com.liferay.mobile.screens.webcontent;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.ddl.ContentParser;
import com.liferay.mobile.screens.ddl.StaticParser;
import com.liferay.mobile.screens.ddl.model.DDMStructure;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.WithDDM;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
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

	public static final String DDM_STRUCTURE = "DDMStructure";

	public WebContent(Parcel in, ClassLoader classLoader) {
		super(in, classLoader);
	}

	public WebContent(Map<String, Object> map, Locale locale) {
		super(map);
		_ddmStructure = new DDMStructure(locale);

		try {
			if (map.containsKey(DDM_STRUCTURE)) {
				HashMap ddmStructure = (HashMap) map.get(DDM_STRUCTURE);
				parseDDMStructure(new JSONObject(ddmStructure));
			}

			String content = getContent(map);

			if (content.contains("dynamic-element")) {
				ContentParser contentParser = new ContentParser();

				List<Field> fields = contentParser.parseContent(_ddmStructure,
					content);

				_ddmStructure.setFields(fields);
				if (_ddmStructure.getFieldCount() > 0) {
					_html = (String) _ddmStructure.getField(0).getCurrentValue();
				}
			}
			else {
				StaticParser staticParser = new StaticParser();
				_html = staticParser.parse(content, locale);
			}
		}
		catch (JSONException e) {
			LiferayLogger.e("Error parsing structure");
		}
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

	public void setHtml(String html) {
		_html = html;
	}

	private String getContent(Map<String, Object> map) {
		return (String) (map.containsKey("content") ? map.get("content") : map.get("modelValues"));
	}

	private DDMStructure _ddmStructure;
	private String _html;
}
