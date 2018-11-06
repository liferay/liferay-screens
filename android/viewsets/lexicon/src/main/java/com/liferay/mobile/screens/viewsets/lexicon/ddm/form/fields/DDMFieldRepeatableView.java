package com.liferay.mobile.screens.viewsets.lexicon.ddm.form.fields;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.viewsets.lexicon.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DDMFieldRepeatableView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldRepeatableView {

    public DDMFieldRepeatableView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DDMFieldRepeatableView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DDMFieldRepeatableView(@NotNull Context context) {
        super(context);
    }

    @Override
    public void onPostValidation(boolean valid) {
        super.onPostValidation(valid);

        View errorView = findViewById(R.id.error_container_view);

        if (errorView != null) {
            errorView.setVisibility(valid ? GONE : VISIBLE);
        }
    }
}
