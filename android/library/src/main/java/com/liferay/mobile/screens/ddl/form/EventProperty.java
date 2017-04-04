package com.liferay.mobile.screens.ddl.form;

import java.util.ArrayList;
import java.util.List;

public class EventProperty {

	private transient EventType eventType;
	private transient Long time;
	private String elementId;
	private String entityType;
	private Long entityId;
	private String elementName;
	private List<String> referrers = new ArrayList<>();

	public EventProperty(EventType eventType, String name, String elementId) {
		this.eventType = eventType;

		this.elementId = elementId;
		this.elementName = name;
	}

	public EventProperty(EventType eventType, String name, Long time) {
		this(name, eventType, null, null, time);
	}

	public EventProperty(String name, EventType eventType, String entityType, Long entityId, Long time) {
		this.eventType = eventType;
		this.entityType = entityType;
		this.entityId = entityId;
		this.elementName = name;
		this.time = time;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getName() {
		return elementName;
	}

	public void setName(String name) {
		this.elementName = name;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public List<String> getReferrers() {
		return referrers;
	}

	public void setReferrers(List<String> referrers) {
		this.referrers = referrers;
	}
}
