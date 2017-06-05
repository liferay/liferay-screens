package com.liferay.mobile.screens.demoform.analytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackingMessage {

	private String messageFormat = "FORMS";
	private String applicationId = "OnlineBanking";
	private String channel = "mobile";

	private MessageContext messageContext;
	private List<TrackingEvent> events = new ArrayList<>();

	public TrackingMessage(MessageContext messageContext, List<TrackingEvent> events) {
		this.messageContext = messageContext;
		this.events = events;
	}

	public TrackingMessage(MessageContext messageContext, TrackingEvent event) {
		this.messageContext = messageContext;
		this.events = Collections.singletonList(event);
	}

	public String getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public MessageContext getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	public List<TrackingEvent> getEvents() {
		return events;
	}

	public void setEvents(List<TrackingEvent> events) {
		this.events = events;
	}
}
