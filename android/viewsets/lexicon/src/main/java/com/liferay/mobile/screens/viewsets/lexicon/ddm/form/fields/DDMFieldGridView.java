package com.liferay.mobile.screens.viewsets.lexicon.ddm.form.fields;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldSelectView;
import com.liferay.mobile.screens.viewsets.lexicon.R;
import com.liferay.mobile.screens.viewsets.lexicon.util.FormViewUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DDMFieldGridView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldGridView {

    public DDMFieldGridView(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DDMFieldGridView(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DDMFieldGridView(@NotNull Context context) {
        super(context);
    }

    @Override
    public void onPostValidation(boolean valid) {
        View errorView = findViewById(R.id.error_view);
        if (errorView != null) {
            errorView.setVisibility(valid ? GONE : VISIBLE);
        }

        int max = getGridLinearLayout().getChildCount();

        for (int i = 0; i < max; i++) {
            DDMFieldGridRowView gridRowView = (DDMFieldGridRowView) getGridLinearLayout().getChildAt(i);

            Drawable drawable;

            drawable = getContext().getResources().getDrawable(R.drawable.lexicon_grid_row_view_error_drawable);
            FormViewUtil.setupBackground(valid ? null : drawable, gridRowView);

            DDLFieldSelectView columnSelectView = gridRowView.getColumnSelectView();

            if (valid) {
                drawable = getContext().getResources().getDrawable(R.drawable.default_grid_edit_text_selector);
            } else {
                drawable = getContext().getResources().getDrawable(R.drawable.lexicon_grid_select_error_drawable);
            }

            FormViewUtil.setupBackground(drawable, columnSelectView.getTextEditText());
        }
    }
}
