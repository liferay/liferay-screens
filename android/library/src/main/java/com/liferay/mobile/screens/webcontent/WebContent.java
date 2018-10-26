package com.liferay.mobile.screens.webcontent;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.ddl.ContentParser;
import com.liferay.mobile.screens.ddl.FieldParser;
import com.liferay.mobile.screens.ddl.model.DDMStructure;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.WithDDM;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class WebContent extends AssetEntry implements WithDDM, Parcelable {

    public static final ClassLoaderCreator<WebContent> CREATOR = new ClassLoaderCreator<WebContent>() {
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
    private DDMStructure ddmStructure;
    private String html;

    public WebContent() {
        super();
    }

    public WebContent(Parcel in, ClassLoader classLoader) {
        super(in, classLoader);
    }

    public WebContent(Map<String, Object> map, Locale locale) {
        super(map);
        ddmStructure = new DDMStructure(locale);

        try {
            if (map.containsKey(DDM_STRUCTURE)) {
                HashMap hashMap = (HashMap) map.get(DDM_STRUCTURE);

                HashMap values = hashMap.containsKey("ddmStructure") ? (HashMap) hashMap.get("ddmStructure") : hashMap;

                parseDDMStructure(new JSONObject(values));
            }

            String content = getContent(map);

            if (content.contains("dynamic-element")) {
                ContentParser contentParser = new ContentParser();

                List<Field> fields = contentParser.parseContent(ddmStructure, content);

                ddmStructure.setFields(fields);
                if (ddmStructure.getFieldCount() > 0) {
                    html = (String) ddmStructure.getField(0).getCurrentValue();
                }
            } else {
                FieldParser fieldParser = new FieldParser();
                html = fieldParser.parseStaticContent(content, locale);
            }
        } catch (JSONException e) {
            LiferayLogger.e("Error parsing structure");
        }
    }

    public WebContent(String html) {
        super(new HashMap<String, Object>());
        this.html = html;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(ddmStructure, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public DDMStructure getDDMStructure() {
        return ddmStructure;
    }

    @Override
    public void parseDDMStructure(JSONObject jsonObject) throws JSONException {
        ddmStructure.parse(jsonObject);
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLocalized(String name) {
        Field field = getDDMStructure().getFieldByName(name);
        if (field != null) {
            return (String) field.getCurrentValue();
        }

        String content = getContent(getValues());

        return new FieldParser().parseField(content, ddmStructure.getLocale(), name);
    }

    public String getTitle() {
        String assetTitle = super.getTitle();
        if (!assetTitle.contains("?xml")) {
            return assetTitle;
        }

        return new FieldParser().parseTitle(assetTitle, ddmStructure.getLocale());
    }

    private String getContent(Map<String, Object> map) {
        return (String) (map.containsKey("content") ? map.get("content") : map.get("modelValues"));
    }

    public String getArticleId() {
        return String.valueOf(values.get("articleId"));
    }
}
