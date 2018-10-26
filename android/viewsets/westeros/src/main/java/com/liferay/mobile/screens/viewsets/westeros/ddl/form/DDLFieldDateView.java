package com.liferay.mobile.screens.viewsets.westeros.ddl.form;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Javier Gamarra
 */
public class DDLFieldDateView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldDateView {

    public DDLFieldDateView(Context context) {
        super(context);
    }

    public DDLFieldDateView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public DDLFieldDateView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    protected int getDatePickerStyle() {
        return R.style.westeros_date_picker;
    }
}