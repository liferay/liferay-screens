# LoginScreenlet for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- Android SDK 4.0 (API Level 14) and above
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The `LoginScreenlet` lets you authenticate portal users in your Android app. The following authentication methods are supported:

- Email address
- Screen name
- User id

When user is successfully authenticated, their attributes are retrieved for use in the app. It's important to note that user credentials and attributes can be stored in a data store. Currently `SharedPreferences` are the only store implemented, but new more secure stores will be added in the future. This stored credentials can be used to perform automatic login in subsequent sessions.

## Module

- Auth

## Views

- Default

TODO picture
![The `LoginScreenlet` using the Default and Flat7 themes.](Images/login.png)

## Portal Configuration

The authentication method should be configured in your Liferay instance. You can set this in the Control Panel by clicking *Portal Settings* and then *Authentication*.

![Setting the authentication method in Liferay Portal.](../../ios/Documentation/Images/portal-auth.png "Liferay portal authentication methods")

For more details, please refer to the [Configuring Portal Settings](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/configuring-portal-settings) section of the User Guide.

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `layoutId` | `@layout` | The layout to be used to show the view.|
|  `companyId` | `number` | When set, authentication is done for a user in the specified company. If the value is `0`, the company specified in `LiferayServerContext` is used. |
|  `authMethod` | `string` | Specifies the authentication method to use. This must match the authentication method configured on the server. You can set this attribute to `email`, `screenName` or `userId`. Default value is `email`.|
|  `credentialsStore ` | `enum` | Posible values: `none`, `auto` and `shared_preferences`. If the value is `shared_preferences` the user credentials and attributes are stored using `SharedPreferences` class. If the value is `none`, the credentials won't be saved at all. If the value if `auto`, the best of the available stores will be used (at this point, it's equivalent to set `shared_preferences`). Default value is `none`.|

## Listener

The `LoginScreenlet` delegates some events to an object that implements the `LoginListener` interface. This interface let you implement the following methods:

| Method | Explanation |
|-----------|-------------| 
|  <pre>onLoginSuccess(User user)</pre> | Called when login successfully completes. The user parameter contains a set of attributes of the logged user. The supported keys are the same as the [portal's User entity](https://github.com/liferay/liferay-portal/blob/6.2.x/portal-impl/src/com/liferay/portal/service.xml#L2227)|
|  <pre>onLoginFailure(Exception e)</pre> | Called when an error occurs in the process.|

