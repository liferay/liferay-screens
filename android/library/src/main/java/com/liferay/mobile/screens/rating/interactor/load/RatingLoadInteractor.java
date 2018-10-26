package com.liferay.mobile.screens.rating.interactor.load;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.rating.connector.ScreensRatingsConnector;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.util.ServiceProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.liferay.mobile.screens.cache.Cache.SEPARATOR;

/**
 * @author Alejandro Hernández
 */
public class RatingLoadInteractor extends BaseCacheReadInteractor<RatingListener, RatingEvent> {

    @Override
    public RatingEvent execute(Object... args) throws Exception {

        long entryId = (long) args[0];
        long classPK = (long) args[1];
        String className = (String) args[2];
        int ratingGroupCounts = (int) args[3];

        validate(entryId, className, classPK);

        JSONObject jsonObject = getRatingsEntries(entryId, classPK, className, ratingGroupCounts);

        return new RatingEvent(jsonObject);
    }

    @Override
    public void onSuccess(RatingEvent event) {
        JSONObject result = event.getJSONObject();
        AssetRating assetRating;

        try {
            assetRating = new AssetRating(result.getLong("classPK"), result.getString("className"),
                toIntArray(result.getJSONArray("ratings")), result.getDouble("average"), result.getDouble("userScore"),
                result.getDouble("totalScore"), result.getInt("totalCount"));
        } catch (JSONException e) {
            event.setException(e);
            onFailure(event);
            return;
        }
        getListener().onRatingOperationSuccess(assetRating);
    }

    @Override
    public void onFailure(RatingEvent event) {
        getListener().error(event.getException(), RatingScreenlet.LOAD_RATINGS_ACTION);
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        long entryId = (long) args[0];
        long classPK = (long) args[1];
        String className = (String) args[2];
        int ratingGroupCounts = (int) args[3];

        return (entryId == 0 ? className + SEPARATOR + classPK : String.valueOf(entryId))
            + SEPARATOR
            + ratingGroupCounts;
    }

    protected int[] toIntArray(JSONArray array) {
        int[] intArray = new int[array.length()];
        for (int i = 0; i < array.length(); i++) {
            intArray[i] = array.optInt(i);
        }
        return intArray;
    }

    private JSONObject getRatingsEntries(long entryId, long classPK, String className, int ratingGroupCounts)
        throws Exception {

        ScreensRatingsConnector connector = ServiceProvider.getInstance().getScreensRatingsConnector(getSession());
        if (entryId != 0) {
            return connector.getRatingsEntries(entryId, ratingGroupCounts);
        } else {
            return connector.getRatingsEntries(classPK, className, ratingGroupCounts);
        }
    }

    private void validate(long entryId, String className, long classPK) {
        if (entryId == 0 && (className == null || classPK == 0)) {
            throw new IllegalArgumentException("Either entryId or className & classPK cannot" + "be empty");
        }
    }
}
