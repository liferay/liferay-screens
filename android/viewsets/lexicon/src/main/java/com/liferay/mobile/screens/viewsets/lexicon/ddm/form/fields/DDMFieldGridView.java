package com.liferay.mobile.screens.viewsets.lexicon.ddm.form.fields;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.widget.EditText;
import com.liferay.mobile.screens.viewsets.lexicon.R;
import com.liferay.mobile.screens.viewsets.lexicon.util.FormViewUtil;

public class DDMFieldGridView
    extends com.liferay.mobile.screens.viewsets.defaultviews.ddm.form.fields.DDMFieldGridView {

    public DDMFieldGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DDMFieldGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DDMFieldGridView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onPostValidation(boolean valid) {
        FormViewUtil.setupErrorView(valid, findViewById(R.id.error_view));

        int max = getGridLinearLayout().getChildCount();

        Drawable backgroundErrorDrawable =
            getContext().getResources().getDrawable(R.drawable.lexicon_grid_row_view_error_drawable);

        Drawable editTextDefaultDrawable =
            getContext().getResources().getDrawable(R.drawable.default_grid_edit_text_selector);

        Drawable editTextErrorDrawable =
            getContext().getResources().getDrawable(R.drawable.lexicon_grid_select_error_drawable);

        for (int i = 0; i < max; i++) {
            DDMFieldGridRowView gridRowView = (DDMFieldGridRowView) getGridLinearLayout().getChildAt(i);
            EditText columnEditText = gridRowView.getColumnSelectView().getTextEditText();

            if (valid) {
                gridRowView.setBackground(null);
                columnEditText.setBackground(editTextDefaultDrawable);
            } else {
                gridRowView.setBackground(backgroundErrorDrawable);
                columnEditText.setBackground(editTextErrorDrawable);
            }
        }
    }
}
