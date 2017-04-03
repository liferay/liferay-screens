package com.liferay.mobile.screens.demoform.analytics;

import com.liferay.mobile.screens.ddl.form.EventProperty;
import com.liferay.mobile.screens.ddl.form.EventType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackingEvent {

	private List<EventProperty> properties = new ArrayList<>();
	private String event;
	private String timestamp;
	private Map<String, Object> additionalInfo = new HashMap<>();
	private Long groupId;

	public TrackingEvent(EventType event, Long groupId, String timestamp, Map<String, Object> additionalInfo,
		EventProperty eventProperty) {
		this.properties = Collections.singletonList(eventProperty);
		this.event = event.getName();
		this.timestamp = timestamp;
		this.additionalInfo = additionalInfo;
		this.groupId = groupId;
	}

	public List<EventProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<EventProperty> properties) {
		this.properties = properties;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, Object> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, Object> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
