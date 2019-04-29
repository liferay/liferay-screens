package com.liferay.mobile.screens.util;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.Nullable;
import android.view.ContextThemeWrapper;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class LiferayLocale {

    private LiferayLocale() {
        super();
    }

    public static String getSupportedLocale(String locale) {
        String localeStr = getSupportedLocaleWithNoDefault(locale);
        return localeStr == null ? "en_US" : localeStr;
    }

    public static Locale getDefaultLocale() {
        return LiferayScreensContext.getContext().getResources().getConfiguration().locale;
    }

    public static String getDefaultSupportedLocale() {
        return getSupportedLocale(getDefaultLocale().getLanguage());
    }

    /**
     * Method to change activity or application locale
     * An activity can be reloaded with a call to setContentView
     **/
    public static void changeLocale(ContextThemeWrapper contextThemeWrapper, Locale newLocale) {
        Resources res = contextThemeWrapper.getResources();
        Configuration conf = res.getConfiguration();

        Locale oldLocale = conf.locale;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(newLocale);
        } else {
            conf.locale = newLocale;
        }
        res.updateConfiguration(conf, res.getDisplayMetrics());

        EventBusUtil.post(new LocaleChanged(newLocale, oldLocale));
    }

    @Nullable
    public static String getSupportedLocaleWithNoDefault(String locale) {
        Locale localeWithoutDefault = getLocaleWithoutDefault(locale);
        return localeWithoutDefault == null ? null : localeWithoutDefault.toString();
    }

    @Nullable
    public static Locale getLocaleWithoutDefault(String locale) {
        locale = locale.substring(0, 2).toLowerCase();
        switch (locale) {
            case "ca":
            case "es":
                return new Locale("es", "ES");
            case "zh":
                return Locale.CHINA;
            case "fi":
                return new Locale("fi", "FI");
            case "fr":
                return Locale.FRANCE;
            case "de":
                return Locale.GERMANY;
            case "iw":
            case "he":
                return new Locale("iw", "IL");
            case "hu":
                return new Locale("hu", "HU");
            case "ja":
                return Locale.JAPAN;
            case "pt":
                return new Locale("pt", "BR");
            case "en":
                return Locale.US;
            default:
                return null;
        }
    }
}
