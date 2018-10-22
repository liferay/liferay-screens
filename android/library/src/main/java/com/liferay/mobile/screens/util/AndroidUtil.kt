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
package com.liferay.mobile.screens.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import android.widget.TextView
import com.liferay.mobile.screens.R
import com.liferay.mobile.screens.ddl.model.DocumentLocalFile
import java.io.InputStream
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.os.Build.VERSION.SDK_INT
import android.text.Html
import android.text.Spanned


/**
 * @author Victor Oliveira
 */
class AndroidUtil {

    companion object {

        @JvmStatic
        fun getUriFromString(uriString: String): Uri {
            return Uri.parse(uriString)
        }

        @JvmStatic
        fun getFileNameFromPath(uriString: String): String {
            return getUriFromString(uriString).lastPathSegment
        }

        @JvmStatic
        fun isConnected(applicationContext: Context): Boolean {
            val connectivityManager = applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

            connectivityManager?.let {
                return it.activeNetworkInfo != null && it.activeNetworkInfo.isConnectedOrConnecting
            }

            return false
        }

        @JvmStatic
        fun openLocalFileInputStream(applicationContext: Context, documentLocalFile: DocumentLocalFile): InputStream {
            val fileUri = AndroidUtil.getUriFromString(documentLocalFile.toString())

            return applicationContext.contentResolver.openInputStream(fileUri)
        }

        @JvmStatic
        @JvmOverloads
        fun showCustomSnackbar(
            view: View, message: String,
            duration: Int, @ColorInt backgroundColor: Int, @ColorInt textColor: Int, @DrawableRes icon: Int? = null) {

            val snackbar = Snackbar.make(view, message, duration)
            snackbar.view.setBackgroundColor(backgroundColor)

            val textView = snackbar.view.findViewById(android.support.design.R.id.snackbar_text) as? TextView

            textView?.let {
                textView.setTextColor(textColor)

                icon?.let {
                    val drawable = ContextCompat.getDrawable(view.context, icon)

                    drawable?.let {
                        DrawableCompat.setTint(drawable, textColor)

                        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                        textView.compoundDrawablePadding =
                            view.context.resources.getDimensionPixelOffset(R.dimen.field_padding)
                    }
                }
            }

            snackbar.show()
        }

        @Suppress("DEPRECATION")
        fun fromHtml(html: String?): Spanned {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
        }

    }
}
