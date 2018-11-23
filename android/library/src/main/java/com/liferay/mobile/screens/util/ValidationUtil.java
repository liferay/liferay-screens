package com.liferay.mobile.screens.util;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.ddl.Validator;
import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Albertinin Mourato
 */
public class ValidationUtil {

    public static Map<String, String> getValidationFromAttributes(Map<String, Object> attributes) {
        if (attributes.get(FormFieldKeys.VALIDATION_KEY) instanceof Map) {
            return (Map<String, String>) attributes.get(FormFieldKeys.VALIDATION_KEY);
        } else {
            return new HashMap<>();
        }
    }

    public static String getErrorMessage(FieldValidationState state, Validator validator) {
        switch (state) {
            case REQUIRED_WITHOUT_VALUE:
                return LiferayScreensContext.getContext().getString(R.string.this_field_is_required);

            case INVALID_BY_LOCAL_RULE:
                return validator.getErrorMessage();

            default:
                return "";
        }

    }
}
