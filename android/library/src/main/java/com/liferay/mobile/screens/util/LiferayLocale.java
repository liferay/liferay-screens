package com.liferay.mobile.screens.util;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;

import com.liferay.mobile.screens.context.LiferayScreensContext;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class LiferayLocale {

	public static String getSupportedLocale(String locale) {
		String localeStr = getSupportedLocaleWithNoDefault(locale);
		return localeStr == null ? "en_US" : localeStr;
	}

	public static Locale getDefaultLocale() {
		return LiferayScreensContext.getContext().getResources().getConfiguration().locale;
	}

	public static String getDefaultSupportedLocale() {
		return getSupportedLocale(getDefaultLocale().getDisplayLanguage());
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
		}
		else {
			conf.locale = newLocale;
		}
		res.updateConfiguration(conf, res.getDisplayMetrics());

		EventBusUtil.post(new LocaleChanged(newLocale, oldLocale));
	}

	@Nullable
	public static String getSupportedLocaleWithNoDefault(String locale) {
		switch (locale) {
			case "ca":
			case "es":
				return locale + "_ES";
			case "zh":
				return locale + "_CN";
			case "fi":
				return locale + "_FI";
			case "fr":
				return locale + "_FR";
			case "de":
				return locale + "_DE";
			case "iw":
			case "he":
				return "iw_IL";
			case "hu":
				return locale + "_HU";
			case "ja":
				return locale + "_JP";
			case "pt":
				return locale + "_BR";
			case "en": {
				return locale + "_US";
			}
			default:
				return null;
		}
	}
}
