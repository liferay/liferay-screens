package com.liferay.mobile.screens.context;

import android.content.Context;
import android.net.Uri;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.File;

/**
 * @author Víctor Galán Grande
 */
public class PicassoScreens {

	public static RequestCreator load(String url) {
		initializePicassoSingleton();

		RequestCreator requestCreator = picasso.load(url);

		return applyOfflinePolicies(requestCreator);
	}

	public static RequestCreator load(Uri uri) {
		initializePicassoSingleton();

		RequestCreator requestCreator = picasso.load(uri);

		return applyOfflinePolicies(requestCreator);
	}

	public static RequestCreator load(File file) {
		initializePicassoSingleton();

		RequestCreator requestCreator = picasso.load(file);

		return applyOfflinePolicies(requestCreator);
	}

	public static RequestCreator load(int resourceId) {
		initializePicassoSingleton();

		RequestCreator requestCreator = picasso.load(resourceId);

		return applyOfflinePolicies(requestCreator);
	}

	public static OfflinePolicy getOfflinePolicy() {
		return offlinePolicy;
	}

	public static void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		PicassoScreens.offlinePolicy = offlinePolicy;
	}

	private static void initializePicassoSingleton() {
		if(picasso == null) {
			synchronized (PicassoScreens.class) {
				Context context = LiferayScreensContext.getContext();
				Downloader downloader = new OkHttpDownloader(LiferayServerContext.getOkHttpClient());
				picasso = new Picasso.Builder(context).downloader(downloader).build();
				picasso.setIndicatorsEnabled(true);
			}
		}
	}

	private static RequestCreator applyOfflinePolicies(RequestCreator picassoRequestCreator) {
		switch (offlinePolicy) {

			case REMOTE_ONLY:
				picassoRequestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
					.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
				break;

			case REMOTE_FIRST:
				picassoRequestCreator.memoryPolicy(MemoryPolicy.NO_CACHE)
					.networkPolicy(NetworkPolicy.NO_CACHE);
				break;

			case CACHE_FIRST:
				break;

			case CACHE_ONLY:
				picassoRequestCreator.networkPolicy(NetworkPolicy.OFFLINE);
				break;
		}

		return picassoRequestCreator;
	}

	private static volatile Picasso picasso;
	private static OfflinePolicy offlinePolicy;
}
