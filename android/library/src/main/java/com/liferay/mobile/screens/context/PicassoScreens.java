package com.liferay.mobile.screens.context;

import android.content.Context;
import android.net.Uri;
import com.liferay.mobile.screens.cache.CachePolicy;
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

    private static volatile Picasso picasso;
    private static volatile Picasso picassoWithoutCache;
    private static CachePolicy cachePolicy = CachePolicy.REMOTE_ONLY;

    private PicassoScreens() {
        super();
    }

    public static RequestCreator load(String url) {
        RequestCreator requestCreator = getPicasso().load(url);

        return applyCachePolicies(requestCreator);
    }

    public static RequestCreator load(Uri uri) {
        RequestCreator requestCreator = getPicasso().load(uri);

        return applyCachePolicies(requestCreator);
    }

    public static RequestCreator load(File file) {
        RequestCreator requestCreator = getPicasso().load(file);

        return applyCachePolicies(requestCreator);
    }

    public static RequestCreator load(int resourceId) {
        RequestCreator requestCreator = getPicasso().load(resourceId);

        return applyCachePolicies(requestCreator);
    }

    public static CachePolicy getCachePolicy() {
        return cachePolicy;
    }

    public static void setCachePolicy(CachePolicy cachePolicy) {
        PicassoScreens.cachePolicy = cachePolicy;
    }

    private static Picasso getPicasso() {

        if (cachePolicy.equals(CachePolicy.REMOTE_ONLY)) {
            if (picassoWithoutCache == null) {
                synchronized (PicassoScreens.class) {
                    Context context = LiferayScreensContext.getContext();
                    Downloader downloader = new OkHttpDownloader(LiferayServerContext.getOkHttpClientNoCache());
                    picassoWithoutCache = new Picasso.Builder(context).downloader(downloader).build();
                }
            }

            return picassoWithoutCache;
        } else if (picasso == null) {
            synchronized (PicassoScreens.class) {
                Context context = LiferayScreensContext.getContext();
                Downloader downloader = new OkHttpDownloader(LiferayServerContext.getOkHttpClient());
                picasso = new Picasso.Builder(context).downloader(downloader).build();
            }
        }

        return picasso;
    }

    private static RequestCreator applyCachePolicies(RequestCreator picassoRequestCreator) {
        switch (cachePolicy) {

            default:
            case REMOTE_ONLY:
                picassoRequestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
                break;

            case REMOTE_FIRST:
                picassoRequestCreator.networkPolicy(NetworkPolicy.NO_CACHE);
                break;

            case CACHE_FIRST:
                break;

            case CACHE_ONLY:
                picassoRequestCreator.networkPolicy(NetworkPolicy.OFFLINE);
                break;
        }

        return picassoRequestCreator;
    }
}
