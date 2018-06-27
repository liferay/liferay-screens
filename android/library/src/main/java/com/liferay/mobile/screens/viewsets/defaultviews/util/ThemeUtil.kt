/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.defaultviews.util

import android.content.Context
import android.content.pm.PackageManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.context.LiferayScreensContext
import com.liferay.mobile.screens.util.LiferayLogger

/**
 * @author Victor Oliveira
 */
class ThemeUtil {

    companion object {
        @JvmStatic
        fun getRequiredSpannable(context: Context): Spannable {
            val requiredAlert = SpannableString(" *")

            val color = context.resources.getColor(R.color.colorRequiredField)
            requiredAlert.setSpan(ForegroundColorSpan(color), 0, requiredAlert.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            return requiredAlert
        }

        @JvmStatic
        fun getLayoutIdentifier(context: Context, fieldNamePrefix: String): Int {
            return getLayoutIdentifier(context, fieldNamePrefix, ThemeUtil.getLayoutTheme(context))
        }

        @JvmStatic
        fun getLayoutIdentifier(context: Context, fieldNamePrefix: String, themeName: String): Int {
            var identifier = context.resources.getIdentifier(
                "${fieldNamePrefix}_$themeName", "layout", context.packageName)

            if (identifier == 0) {
                identifier = context.resources.getIdentifier(
                    "${fieldNamePrefix}_default", "layout", context.packageName)
            }

            return identifier
        }

        @JvmStatic
        fun getLayoutTheme(context: Context): String {
            var result: String? = substringThemeName(getActivityTheme(context))

            if (result == null) {
                result = substringThemeName(getApplicationTheme(context) ?: "default")
            }
            return result!!
        }

        @JvmStatic
        private fun getActivityTheme(context: Context): String? {
            try {
                val outValue = TypedValue()
                LiferayScreensContext.getActivityFromContext(context)
                    .theme
                    .resolveAttribute(R.attr.themeName, outValue, true)
                return outValue.string as String
            } catch (e: Exception) {
                LiferayLogger.d("Screens theme not found")
            }

            return null
        }

        @JvmStatic
        private fun getApplicationTheme(context: Context): String? {
            try {
                val ctx = context.applicationContext
                val packageName = ctx.packageName
                val packageInfo = ctx.packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA)
                val applicationThemeId = packageInfo.applicationInfo.theme
                return context.resources.getResourceEntryName(applicationThemeId)
            } catch (e: Exception) {
                LiferayLogger.d("Screens theme not found")
            }

            return null
        }

        @JvmStatic
        private fun substringThemeName(themeName: String?): String? {
            return if (themeName != null && themeName.contains("_theme")) {
                themeName.substring(0, themeName.indexOf("_theme"))
            } else themeName
        }
    }

}