package com.liferay.mobile.screens.user.display;

import com.liferay.mobile.screens.asset.list.AssetEntry;
import java.util.Map;

/**
 * @author Sarai Díaz García
 */
public class UserAsset extends AssetEntry {

	//TODO this class must be merge with User class
	public UserAsset(Map<String, Object> map) {
		super(map);
	}

	public Map<String, Object> getUserAsset() {
		return (Map<String, Object>) values.get("user");
	}

	public String getGreeting() {
		return (String) getUserAsset().get("greeting");
	}

	public String getFirstName() {
		return (String) getUserAsset().get("firstName");
	}

	public String getLastName() {
		return (String) getUserAsset().get("lastName");
	}

	public String getJobTitle() {
		return (String) getUserAsset().get("jobTitle");
	}

	public String getEmail() {
		return (String) getUserAsset().get("emailAddress");
	}

	public String getScreenName() {
		return (String) getUserAsset().get("screenName");
	}

	public long getId() {
		return Long.parseLong(getUserAsset().get("userId").toString());
	}
}
