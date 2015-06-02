package com.liferay.mobile.pushnotifications.download;

import android.content.Context;
import android.graphics.Bitmap;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.dlfileentry.DLFileEntryService;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DownloadPicture {

	public Bitmap downloadPicture(Context context, Session session, String server,
								  String uuid, Long groupId, int targetWidth) throws Exception {
		DLFileEntryService entryService = new DLFileEntryService(session);
		JSONObject result = entryService.getFileEntryByUuidAndGroupId(uuid, groupId);

		Integer folderId = result.getInt("folderId");
		String name = result.getString("name");

		String url = server + "documents/" + groupId + "/" + folderId + "/" + name + "/" + uuid;

		return Picasso.with(context).load(url).resize(targetWidth, targetWidth).get();
	}
}
