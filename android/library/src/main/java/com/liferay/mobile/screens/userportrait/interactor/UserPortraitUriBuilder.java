package com.liferay.mobile.screens.userportrait.interactor;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserPortraitUriBuilder {

    private static final int MAX_SIZE = 100 * 1024 * 1024;

    public OkHttpClient getUserPortraitClient(Context context) {
        OkHttpClient client = new OkHttpClient();
        client.setCache(new Cache(context.getCacheDir(), MAX_SIZE));
        return client;
    }

    public Uri getUserPortraitUri(String server, boolean male, long portraitId, String uuid) {
        String maleString = male ? "male" : "female";
        String url =
            server + "/image/user_" + maleString + "_portrait?img_id=" + portraitId + "&img_id_token=" + getSHA1String(
                uuid);
        return Uri.parse(url);
    }

    private String getSHA1String(String uuid) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            digest.update(uuid.getBytes("UTF-8"));

            byte[] bytes = digest.digest();
            String token = Base64.encodeToString(bytes, Base64.NO_WRAP);

            return URLEncoder.encode(token, "UTF8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LiferayLogger.e("Algorithm not found!", e);
        }
        return null;
    }
}
