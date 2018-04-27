package com.liferay.mobile.screens.ddm.form.model;

import android.os.Parcel;

import com.liferay.mobile.screens.ddl.model.StringWithOptionsField;

import java.util.Locale;
import java.util.Map;

/**
 * @author Paulo Cruz
 */
public class CheckboxMultipleField extends StringWithOptionsField {

    private boolean showAsSwitcher;

    public CheckboxMultipleField() {
    }

    public CheckboxMultipleField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);

        showAsSwitcher = Boolean.valueOf(attributes.get("showAsSwitcher").toString());
    }

    public CheckboxMultipleField(Parcel in, ClassLoader loader) {
        super(in, loader);
    }

    public boolean isShowAsSwitcher() {
        return showAsSwitcher;
    }

    public void setShowAsSwitcher(boolean showAsSwitcher) {
        this.showAsSwitcher = showAsSwitcher;
    }
}
