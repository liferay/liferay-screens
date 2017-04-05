package com.liferay.mobile.screens.ddl.form;

import java.util.ArrayList;
import java.util.List;

public class EventProperty {

	private transient EventType eventType;
	private transient Long time;
	private String elementId;
	private String elementName;

	private Long entityId;
	private String entityName;
	private String entityType;
	private String lastElementName;

	private List<String> referrers = new ArrayList<>();
	private String lastElementId;
	private String elementLabel;

	public EventProperty(EventType eventType, String elementName, String elementId) {
		this.eventType = eventType;
		this.elementName = elementName;

		this.elementId = elementId;
	}

	public EventProperty(EventType eventType, String elementName, Long time) {
		this.eventType = eventType;
		this.elementName = elementName;

		this.time = time;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
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

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setLastElementName(String lastElementName) {
		this.lastElementName = lastElementName;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getLastElementName() {
		return lastElementName;
	}

	public void setLastElementId(String lastElementId) {
		this.lastElementId = lastElementId;
	}

	public String getElementLabel() {
		return elementLabel;
	}

	public void setElementLabel(String elementLabel) {
		this.elementLabel = elementLabel;
	}
}
