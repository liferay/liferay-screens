package com.liferay.mobile.screens.base.thread;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public interface IdCache {

	String getId();

	String getUserId();

	long getGroupId();

	Locale getLocale();

}