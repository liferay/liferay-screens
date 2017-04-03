package com.liferay.mobile.screens.demoform.analytics;

import java.util.UUID;

public class MessageContext {

	private Long companyId;
	private String locale;
	private Long userId;
	private String userName;
	private boolean signedIn;
	private String deviceId;
	private String deviceType;
	private MessageLocation location;
	private String ipAddress;
	private String sessionId;

	public MessageContext(Long companyId, String locale, Long userId, String userName, boolean signedIn,
		String deviceId, String deviceType, MessageLocation location, String ipAddress, UUID sessionId) {
		this.companyId = companyId;
		this.locale = locale;
		this.userId = userId;
		this.userName = userName;
		this.signedIn = signedIn;
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.location = location;
		this.ipAddress = ipAddress;
		this.sessionId = sessionId.toString();
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isSignedIn() {
		return signedIn;
	}

	public void setSignedIn(boolean signedIn) {
		this.signedIn = signedIn;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public MessageLocation getLocation() {
		return location;
	}

	public void setLocation(MessageLocation location) {
		this.location = location;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
