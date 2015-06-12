# How to add Android Push support to your project

To support Push Notifications ([GCM](https://developers.google.com/cloud-messaging/)) in your Android project there are several steps you will have to follow.

## Create a Google Project and enable Push

First we will have to create a Google project, generate a `SENDER ID` and an `API key` and enable Push Notifications. Let's see how can we do it:

* First, you have to enter in the [developer console](https://console.developers.google.com/) and create a project. After creation, the `project number` (next to your project id) will act as our `SENDER_ID`. 

	![An example app with the project number](project_number.png)

* You will have to enable Push Notifications, go to `APIs & auth`, `APIs`, `Cloud Messaging for Android` and click on `Enable API`.
* After enabling Cloud Messaging we need a server key. You can easily create one from `APIs & auth`, `Credentials` and `Create new Key` (Server Key), just select `Create` and copy the `API Key` string, it will act as our `API Key` in our Liferay Portal.

	![Creating a server key](server_key.png)

Copy those two keys because we are going to use them on later steps.

## Install and configure Push Plugin in Liferay Portal

Luckily for us there is a [plugin](http://www.liferay.com/es/marketplace/-/mp/application/48439053) that streamlines all the server configuration.

Just install that plugin and configure (through the control panel) the `API Key`, using the server key we generated in the previous steps.

![Configure the server key](push_portlet.png)

Our server is now ready to notify Android applications (and with few configuration steps more it'll be able to send to iOS applications, Windows Phone and SMS)

## Using Liferay Push in your Android Application

We offer a [Liferay Push Client for Android](https://github.com/brunofarache/liferay-push-android) that streamlines all complex flow of registering a device for receiving and sending push notifications.

All the required steps are explained in detail in the [documentation](https://github.com/brunofarache/liferay-push-android) but we'll highlight the important steps:

* Add a new gradle dependency to Android Push

	```groovy
	dependencies {
	
		...
		
		compile 'com.liferay.mobile:liferay-push:1.0.2'
	}
	```
	
* Register your device in GCM with your sender id (the project number, generated in previous steps):

	```java
	Session session = new SessionImpl(YOUR_SERVER, new BasicAuthentication(YOUR_USER, YOUR_PASSWORD));
	
	Push.with(session).register(this, YOUR_SENDER_ID);
	```
	
	or easier if you are already using Liferay Screens:
	
	```java
	Push.with(SessionContext.createSessionFromCurrentSession()).register(this, YOUR_SENDER_ID);
	```

* And that's all! you can attach a listener to store the registration id or to process the notification sent to the activity (setting `onPushNotification()`). You can also register a receiver and service to process the notification (as shown in the example project).

We recommend using the [`PushNotifications`](https://github.com/liferay/liferay-screens/tree/master/android/samples) project as a template, specifically the `PushActivity` class.

## Using Liferay Push in your server

It's really easy to send notifications to your Android users from Liferay Portal. Using the Push Notification plugin it's just a matter of sending the user ids and the message content:

```java
PushNotificationsDeviceLocalServiceUtil.sendPushNotification(userIds, content);
```

We have created a full example of using a [hook](https://www.liferay.com/es/documentation/liferay-portal/6.2/development/-/ai/customize-and-extend-functionality-hooks-liferay-portal-6-2-dev-guide-en) to send a push notification each time a user creates a new DDL record or updates one existing. It's available [here](https://github.com/nhpatt/push-with-ddl-hook).

Remember that the new project should depend on the push-notifications-portlet (in `liferay-plugin-package.properties`)

```xml
required-deployment-contexts=\
    push-notifications-portlet
```

and link your Java class with the model you want to listen, for example:

```xml
value.object.listener.com.liferay.portlet.dynamicdatalists.model.DDLRecord=com.liferay.push.hooks.DDLRecordModelListener
```