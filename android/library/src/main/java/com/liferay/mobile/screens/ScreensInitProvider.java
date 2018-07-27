package com.liferay.mobile.screens;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.thingscreenlet.delegates.ConverterDelegate;

public class ScreensInitProvider extends ContentProvider {

	@Override
	public boolean onCreate() {
		Context context = getContext().getApplicationContext();
		LiferayScreensContext.init(context);

		ConverterDelegate.initializeConverter();
		return true;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
		String sortOrder) {
		return null;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public void attachInfo(Context context, ProviderInfo providerInfo) {
		if (providerInfo == null) {
			throw new NullPointerException("ScreensInitProvider ProviderInfo cannot be null.");
		}
		if (".screensinitprovider".equals(providerInfo.authority)) {
			throw new IllegalStateException(
				"Incorrect provider authority in manifest. Most likely due to a missing applicationId variable in "
					+ "application\'s build.gradle.");
		}
		super.attachInfo(context, providerInfo);
	}
}
