package com.liferay.mobile.screens.viewsets.lexicon.ddm.form.fields;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.viewsets.lexicon.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Paulo Cruz
 */
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
    public int getRepeatableButtonDrawableId() {
        return R.drawable.lexicon_button_repeatable_drawable;
    }
}
