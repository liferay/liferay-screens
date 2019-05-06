package com.liferay.mobile.screens.viewsets.lexicon.ddl.form.fields;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import com.liferay.mobile.screens.viewsets.lexicon.R;

/**
 * @author Victor Oliveira
 */

public class DDLFieldGeoView extends com.liferay.mobile.screens.viewsets.defaultviews.ddl.form.fields.DDLFieldGeoView {

    public DDLFieldGeoView(Context context) {
        super(context);
    }

    public DDLFieldGeoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DDLFieldGeoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DDLFieldGeoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onPostValidation(boolean valid) {
        Drawable drawable;
        String errorText = null;
        if (valid) {
            drawable = getContext().getResources().getDrawable(R.drawable.lexicon_edit_text_drawable);
        } else {
            errorText = getContext().getResources().getString(com.liferay.mobile.screens.R.string.invalid);
            drawable = getContext().getResources().getDrawable(R.drawable.lexicon_edit_text_error_drawable);
        }

        if (labelTextView != null) {
            labelTextView.setError(errorText);
        } else {
            latitudeEditText.setError(errorText);
            longitudeEditText.setError(errorText);
        }

        latitudeEditText.setBackground(drawable);
        longitudeEditText.setBackground(drawable);
    }
}
