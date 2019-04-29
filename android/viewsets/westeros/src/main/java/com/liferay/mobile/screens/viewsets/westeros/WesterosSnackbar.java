package com.liferay.mobile.screens.viewsets.westeros;

import android.app.Activity;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;

/**
 * @author Javier Gamarra
 */
public class WesterosSnackbar {

    private WesterosSnackbar() {
        super();
    }

    public static void showSnackbar(Activity activity, String message, int color) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, color));
        snackbar.getView().setMinimumHeight(50);
        snackbar.show();
    }
}
