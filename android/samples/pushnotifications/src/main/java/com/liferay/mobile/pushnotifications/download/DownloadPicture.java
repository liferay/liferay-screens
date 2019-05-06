package com.liferay.mobile.pushnotifications.download;

import android.content.Context;
import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DownloadPicture {

    public RequestCreator createRequest(Context context, JSONObject result, String server, int targetWidth)
        throws JSONException {

        String url = constructUrl(result, server);
        return createPicassoRequest(context, targetWidth, url);
    }

    @NonNull
    private String constructUrl(JSONObject result, String server) throws JSONException {
        Integer groupId = result.getInt("groupId");
        Integer folderId = result.getInt("folderId");
        String name = result.getString("name");
        String uuid = result.getString("uuid");

        return server + "documents/" + groupId + "/" + folderId + "/" + name + "/" + uuid;
    }

    private RequestCreator createPicassoRequest(Context context, int size, String url) {
        return Picasso.with(context).load(url).resize(size, size);
    }
}
