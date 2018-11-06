package com.liferay.mobile.screens.viewsets.lexicon.ddm.form.fields;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.viewsets.lexicon.R;

public class DDMFieldCheckboxMultipleView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldCheckboxMultipleView {

    public DDMFieldCheckboxMultipleView(Context context) {
        super(context);
    }

    public DDMFieldCheckboxMultipleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DDMFieldCheckboxMultipleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onPostValidation(boolean valid) {
        View errorView = findViewById(R.id.error_view);
        if (errorView != null) {
            errorView.setVisibility(valid ? GONE : VISIBLE);
        }
    }
}
