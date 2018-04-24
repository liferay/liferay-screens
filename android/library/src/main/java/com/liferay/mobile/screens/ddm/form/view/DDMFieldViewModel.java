package com.liferay.mobile.screens.ddm.form.view;

import android.view.View;

import com.liferay.mobile.screens.ddm.form.model.DDMField;

/**
 * @author Paulo Cruz
 */
public interface DDMFieldViewModel<T extends DDMField> {

    /**
     * Returns the DDM field.
     */
    T getField();

    /**
     * Sets a DDM field.
     */
    void setField(T field);

    /**
     * Call this method for refreshing the DDM field.
     */
    void refresh();

    /**
     * Called with the validation result.
     */
    void onPostValidation(boolean valid);

    /**
     * Gets the parent view.
     */
    View getParentView();

    /**
     * Sets the parent view.
     */
    void setParentView(View view);

    void setUpdateMode(boolean enabled);
}
