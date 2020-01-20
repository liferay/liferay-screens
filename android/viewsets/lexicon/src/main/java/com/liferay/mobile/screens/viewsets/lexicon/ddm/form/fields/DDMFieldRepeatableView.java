package com.liferay.mobile.screens.viewsets.lexicon.ddm.form.fields;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.liferay.mobile.screens.viewsets.lexicon.R;

import com.liferay.mobile.screens.viewsets.lexicon.util.FormViewUtil;

public class DDMFieldRepeatableView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldRepeatableView {

    public DDMFieldRepeatableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DDMFieldRepeatableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DDMFieldRepeatableView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onPostValidation(boolean valid) {
        super.onPostValidation(valid);
    }
}
