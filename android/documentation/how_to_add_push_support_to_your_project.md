### WORK IN PROGRESS ###

# How to add Push support to your project

To support Push Notifications ([GCM](https://developers.google.com/cloud-messaging/)) in your Android project there are several steps you will have to follow.

## Create a Google Project and enable Push

First we will have to create a Google project, generate a `SENDER ID` and an `API key` and enable Push Notifications. Let's see how can we do it:

* First, you have to enter in the [developer console](https://console.developers.google.com/) and create a project. After creation, the `project number` (next to your project id) will act as our `SENDER_ID`. 
* You will have to enable Push Notifications from `APIs & auth`, `APIs`, `Cloud Messaging for Android` and click on `Enable API`.
* After enabling Cloud Messaging we need a server key. You can easily create one from `APIs & auth`, `Credentials` and `Create new Key` (Server Key), just select `Create` and copy the `API Key` string, it will act as our `API Key` in our Liferay Portal.

Copy those 2 keys because we are going to use them on later steps.

## Install and configure Push Plugin

Luckily for us there is a [plugin](http://www.liferay.com/es/marketplace/-/mp/application/48439053) that streamlines all the server configuration.

Just install that plugin and configure (through the control panel) the `API Key`, using the key we generated in the previous steps.

* Create a hook that sends a push notification when creating or updating a DDL record.
* Add a new dependency to Android Push
* Configure sender id in your project
* Use Liferay Push with the base activity
	* If you are not using Liferay Push...
* Configure permissions
