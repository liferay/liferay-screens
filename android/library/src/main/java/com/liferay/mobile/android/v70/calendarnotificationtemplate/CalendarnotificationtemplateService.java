/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.android.v70.calendarnotificationtemplate;

import com.liferay.mobile.android.http.file.UploadData;
import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class CalendarnotificationtemplateService extends BaseService {

	public CalendarnotificationtemplateService(Session session) {
		super(session);
	}

	public JSONObject addCalendarNotificationTemplate(long calendarId, JSONObjectWrapper notificationType, String notificationTypeSettings, JSONObjectWrapper notificationTemplateType, String subject, String body, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			mangleWrapper(_params, "notificationType", "com.liferay.calendar.notification.NotificationType", notificationType);
			_params.put("notificationTypeSettings", checkNull(notificationTypeSettings));
			mangleWrapper(_params, "notificationTemplateType", "com.liferay.calendar.notification.NotificationTemplateType", notificationTemplateType);
			_params.put("subject", checkNull(subject));
			_params.put("body", checkNull(body));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarnotificationtemplate/add-calendar-notification-template", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateCalendarNotificationTemplate(long calendarNotificationTemplateId, String notificationTypeSettings, String subject, String body, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarNotificationTemplateId", calendarNotificationTemplateId);
			_params.put("notificationTypeSettings", checkNull(notificationTypeSettings));
			_params.put("subject", checkNull(subject));
			_params.put("body", checkNull(body));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarnotificationtemplate/update-calendar-notification-template", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

}