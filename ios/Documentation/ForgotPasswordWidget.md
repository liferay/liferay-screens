# ForgotPasswordWidget for iOS

## Important note
__This product is under heavy development and most of the features aren't ready to be used in production.
It's public, following our Open Source philosophy, in order to allow developers to preview the technology and contribute with feedback, ideas and bug fixes.__

## Features
- Send an email to an already registered user with the new password or reset link (depending on server configuration)
- Supports the following three authentication methods:
	- Email address
	- Screen name
	- User id

##Module
Auth

## Themes
- Default
- Flat7

![Forgot password widget using Default and Flat7 themes](Images/forgotpwd.png "Forgot password widget using Default and Flat7 themes")

## Requirements

- XCode 6.0 or above
- iOS 8 SDK
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatiblity

- iOS 7 and above

## Portal configuration

### Authentication method
Note that the authentication method configured in the portal can be different than the used in this widget. It's *perfectly fine* to allow users to remember their password providing their email address but configure the authentication method could to use screen name.

### Password reset
Depending on portal configuration `Control Panel - Portal Settings - Authentication`, the forgot password feature could be enabled or not.

![](Images/password-reset.png)

- If both options are unchecked, this feature will be disabled.
- If both options are checked, an email with reset link will be sent
- If first option is checked and second one is unchecked, an email with new password will be sent.

Refer to [Configuring Portal Settings](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/portal-settings-liferay-portal-6-2-user-guide-16-en) section for more details.


### Anonymous request
This request is done without the user being logged in, but authentication is needed to call the API. It's recommended that the portal administrator creates a specific user with minimal permissions in order to allow this operation.


## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `anonymousApiUserName` | `string` | The user name, email adress, or userId (depending on portal's authentication method) to use for authenticate this request |
|  `anoymousApiPassword` | `string` | The password to be used to authenticate this request |
|  `companyId` | `number` | When set, the authentication is done for a user within the especified company. If the value is 0, the company especified in `LiferayServerContext` will be used. |
|  `authMethod` | `string` | The information requested to the user: `email`, `screenName` or `userId` |


## Delegate

ForgotPasswordWidget delegates some event in an object that conforms `ForgotPasswordWidgetDelegate` protocol.
This protocol allows to implement the following methods:

- `onForgotPasswordResponse(boolean)`: called when email was successfully sent. The boolean parameter indicates whether a new password was generated or a reset link was sent.
- `onForgotPasswordError(error)`: called when an error happened in the process. The NSError object describes the error occurred.



    
    
