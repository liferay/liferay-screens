# SignUpScreenlet for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- Android SDK 4.0 (API Level 14) and above
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The main function of the `SignUpScreenlet` is to create a new user in your Liferay instance. For example, by using the `SignUpScreenlet` a new user of your app can become a new user in your portal. You can also use this screenlet to save the credentials of the new user in their device. This enables auto login for future sessions. The screenlet also supports navigation of form fields from the keyboard of the user's device.

## Module

- Auth

## Views

- Default

PIcture
![The `SignUpScreenlet` with the Default and Flat7 themes.](Images/signup.png)

## Portal Configuration

The configuration related to the `SignUpScreenlet` can be set in the Control Panel by clicking *Portal Settings* and then *Authentication*. These settings are shown in the following screenshot:

![The portal settings related to the `SignUpScreenlet`.](../../ios/Documentation/Images/portal-signup.png)

For more details, please refer to the [Configuring Portal Settings](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/configuring-portal-settings) section of the User Guide.

## Anonymous Request

An anonymous request can be done without the user being logged in. However, authentication is needed to call the API. To allow this operation, it's recommended that the portal administrator create a specific user with minimal permissions.

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `layoutId` | `@layout` | The layout to be used to show the view.|
|  `anonymousApiUserName` | `string` | The user name, email address, or user ID (depending on the portal's authentication method) to use for authenticating the request. |
|  `anoymousApiPassword` | `string` | The password for use in authenticating the request. |
|  `companyId` | `number` | When set, authentication is done for a user in the specified company. If the value is `0`, the company specified in `LiferayServerContext` is used. |
|  `autoLogin` | `boolean` | Sets whether or not the user is logged in automatically after a successful sign up. |
|  `credentialsStore ` | `enum` | Posible values: `none`, `auto` and `shared_preferences`. If the value is `shared_preferences` the user credentials and attributes are stored using `SharedPreferences` class. If the value is `none`, the credentials won't be saved at all. If the value if `auto`, the best of the available stores will be used (at this point, it's equivalent to set `shared_preferences`). Default value is `none`.|

## Listener

The `LoginScreenlet` delegates some events to an object that implements the `LoginListener` interface. This interface let you implement the following methods:

| Method | Explanation |
|-----------|-------------| 
|  <pre>onSignUpSuccess(User user)</pre> | Called when sign up successfully completes. The user parameter contains a set of attributes of the created user. The supported keys are the same as the [portal's User entity](https://github.com/liferay/liferay-portal/blob/6.2.x/portal-impl/src/com/liferay/portal/service.xml#L2227)|
|  <pre>onSignUpFailure(Exception e)</pre> | Called when an error occurs in the process.|
