package com.liferay.mobile.screens.rating.interactor;

import com.liferay.mobile.screens.rating.RatingEntry;
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Alejandro Hernández
 */
public class RatingEntryFactory {

	public static List<RatingEntry> createEntryList(JSONArray jsonArray) throws JSONException {
		List<RatingEntry> ratings = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			ratings.add(createEntry(jsonArray.getJSONObject(i)));
		}
		return ratings;
	}

	public static RatingEntry createEntry(JSONObject object) throws JSONException {
		double score = object.getDouble("score");
		RatingEntry entry = new RatingEntry(JSONUtil.toMap(object));
		entry.setScore(score);
		return entry;
	}
}
