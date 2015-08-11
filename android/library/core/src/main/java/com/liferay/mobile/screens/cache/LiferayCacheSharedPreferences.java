package com.liferay.mobile.screens.cache;

//FIXME REMOVE
public class LiferayCacheSharedPreferences {
	public LiferayCacheSharedPreferences(CachedType storageType) {

	}
	
//
//    public static void clearCache() {
//        SharedPreferences preferences = getSharedPreferences();
//        SharedPreferences.Editor edit = preferences.edit();
//        edit.clear();
//        edit.apply();
//    }

//    private static SharedPreferences getSharedPreferences() {
//        return LiferayScreensContext.getContext().getSharedPreferences(AT_PREFERENCES, Context.MODE_PRIVATE);
//    }
//
//    public static void storeAudienceResults(final Map<String, Set<AudienceTargetingResult>> results) {
//
//        SharedPreferences preferences = getSharedPreferences();
//        SharedPreferences.Editor edit = preferences.edit();
//
//        Set<String> placeholderIds = new HashSet<>();
//
//        for (String placeholderId : results.keySet()) {
//            Set<String> jsonObjects = new HashSet<>();
//
//            placeholderIds.add(placeholderId);
//
//            Set<AudienceTargetingResult> audienceTargetingResults = results.get(placeholderId);
//            for (AudienceTargetingResult result : audienceTargetingResults) {
//                jsonObjects.add(result.getObject().toString());
//            }
//
//            edit.putStringSet(AT_CACHED_RESULTS + placeholderId, jsonObjects);
//        }
//
//        edit.putStringSet(AT_CACHED_PLACEHOLDERS, placeholderIds);
//        edit.putLong(AT_CACHED_USER_CONTEXT, SessionContext.getLoggedUser().getId());
//        edit.apply();
//    }
//
//    public static AudienceTargetingScreenletsLoadedEvent restoreAudienceResults() {
//        JSONArray jsonArray = new JSONArray();
//        try {
//            SharedPreferences preferences = getSharedPreferences();
//            Set<String> placeholderIds = preferences.getStringSet(AT_CACHED_PLACEHOLDERS, new HashSet<String>());
//
//            for (String placeholderId : placeholderIds) {
//
//                Set<String> values = preferences.getStringSet(AT_CACHED_RESULTS + placeholderId, new HashSet<String>());
//
//                for (String value : values) {
//                    jsonArray.put(new JSONObject(value));
//                }
//            }
//        }
//        catch (JSONException e) {
//            LiferayLogger.e("Error restoring audience targeting objects", e);
//        }
//        return new AudienceTargetingScreenletsLoadedEvent(AT_RESULTS_ID, jsonArray);
//    }
//
//    public static boolean hasCachedAudienceResults(String placeholderId) {
//        SharedPreferences preferences = getSharedPreferences();
//        Long cachedUserContext = preferences.getLong(AT_CACHED_USER_CONTEXT, 0);
//
//        boolean userCachedIsTheSameAsLoggedIn = cachedUserContext.equals(SessionContext.getLoggedUser().getId());
//        boolean placeholderCached = preferences.contains(AT_CACHED_RESULTS + placeholderId);
//
//        return placeholderCached && userCachedIsTheSameAsLoggedIn;
//    }
//
//    public static boolean hasCachedAudienceResults() {
//        return hasCachedAudienceResults("");
//    }
//
//
//    public static final int AT_RESULTS_ID = 0;
//    public static final String AT_PREFERENCES = "AT_PREFERENCES";
//    public static final String AT_CACHED_PLACEHOLDERS = "AT_CACHED_PLACEHOLDERS";
//    public static final String AT_CACHED_RESULTS = "AT_CACHED_RESULTS";
//    public static final String AT_CACHED_USER_CONTEXT = "AT_CACHED_USER_CONTEXT";
}
