/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.ddl.DDMStructureParser;
import com.liferay.mobile.screens.ddl.JsonParser;
import com.liferay.mobile.screens.ddl.XSDParser;
import com.liferay.mobile.screens.ddm.form.model.FormPage;
import com.liferay.mobile.screens.ddm.form.model.SuccessPage;
import com.liferay.mobile.screens.util.LiferayLocale;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
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

    private List<FormPage> pages = new ArrayList<>();
    private SuccessPage successPage;

    public DDMStructure() {
        super();
    }

    public DDMStructure(String name, String description, List<FormPage> pages) {
        this.name = name;
        this.description = description;
        this.pages = pages;

        for (FormPage page : pages) {
            this.fields.addAll(page.getFields());
        }

        parsed = true;
    }

    public DDMStructure(String name, String description, List<FormPage> pages, SuccessPage successPage) {
        this(name, description, pages);
        this.successPage = successPage;
    }

    public DDMStructure(Locale locale) {
        this.locale = locale;
        parsed = false;
    }

    protected DDMStructure(Parcel in, ClassLoader classLoader) {
        Parcelable[] fieldsArray = in.readParcelableArray(Field.class.getClassLoader());
        fields = new ArrayList(Arrays.asList(fieldsArray));

        locale = (Locale) in.readSerializable();
        name = in.readString();
        description = in.readString();

        Parcelable[] pagesArray = in.readParcelableArray(FormPage.class.getClassLoader());
        pages = new ArrayList(Arrays.asList(pagesArray));

        successPage = in.readParcelable(SuccessPage.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(fields.toArray(new Field[0]), flags);
        dest.writeSerializable(locale);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeParcelableArray(pages.toArray(new FormPage[0]), flags);
        dest.writeParcelable(successPage, flags);
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

    public List<FormPage> getPages() {
        return pages;
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
