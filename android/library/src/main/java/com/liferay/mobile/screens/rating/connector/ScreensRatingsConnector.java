package com.liferay.mobile.screens.rating.connector;

import org.json.JSONObject;

public interface ScreensRatingsConnector {
    JSONObject deleteRatingsEntry(long classPK, String className, int ratingGroupCounts) throws Exception;

    JSONObject getRatingsEntries(long entryId, int ratingGroupCounts) throws Exception;

    JSONObject getRatingsEntries(long classPK, String className, int ratingGroupCounts) throws Exception;

    JSONObject updateRatingsEntry(long classPK, String className, double score, int ratingGroupCounts) throws Exception;
}
