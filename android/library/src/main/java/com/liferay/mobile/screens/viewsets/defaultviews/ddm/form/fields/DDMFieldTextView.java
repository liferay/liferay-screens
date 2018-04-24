package com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.ddm.form.model.DDMStringField;

/**
 * @author Paulo Cruz
 */
public class DDMFieldTextView extends BaseDDMFieldTextView<DDMStringField> {

    public DDMFieldTextView(Context context) {
        super(context);
    }

    public DDMFieldTextView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public DDMFieldTextView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    protected void onTextChanged(String text) {

    }
}
