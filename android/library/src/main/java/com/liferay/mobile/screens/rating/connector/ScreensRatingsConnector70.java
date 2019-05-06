package com.liferay.mobile.screens.rating.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.ScreensratingsentryService;
import org.json.JSONObject;

public class ScreensRatingsConnector70 implements ScreensRatingsConnector {
    private final ScreensratingsentryService screensratingsentryService;

    public ScreensRatingsConnector70(Session session) {
        screensratingsentryService = new ScreensratingsentryService(session);
    }

    @Override
    public JSONObject deleteRatingsEntry(long classPK, String className, int ratingGroupCounts) throws Exception {
        return screensratingsentryService.deleteRatingsEntry(classPK, className, ratingGroupCounts);
    }

    @Override
    public JSONObject getRatingsEntries(long entryId, int ratingGroupCounts) throws Exception {
        return screensratingsentryService.getRatingsEntries(entryId, ratingGroupCounts);
    }

    @Override
    public JSONObject getRatingsEntries(long classPK, String className, int ratingGroupCounts) throws Exception {
        return screensratingsentryService.getRatingsEntries(classPK, className, ratingGroupCounts);
    }

    @Override
    public JSONObject updateRatingsEntry(long classPK, String className, double score, int ratingGroupCounts)
        throws Exception {
        return screensratingsentryService.updateRatingsEntry(classPK, className, score, ratingGroupCounts);
    }
}
