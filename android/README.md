# Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

The Android implementation for Liferay Screens includes the components (*screenlets*) library and a sample project.

In Liferay Screens, a *screenlet* is a visual component that is connected to Liferay Portal's functionality. The screenlet is responsible for presenting the information using a specific UI, enabling user interaction to receive user's input, convert user's interactions to server requests or data manipulation, handling communication between the server and the app and at the end, present to the user the result of the server resquests.

Each screenlet is tied to one or more services exposed by [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en). When the screenlet needs to show some information to the user, it relies on a View. View sets can be contributed by third parties and are fully pluggable, so you're not limited to one specific look and feel. The Screens library provides a standard view set called *Default* and another sample one called *Material*.

![The SignUp screenlet using Default and Flat7 themes](Documentation/Images/screens-phone2.png)

To learn more details about the architecture of Screen for Android, please see the [library documentation page](library/README.md).

## Requirements

Development of Android apps using Liferay Screens requires the following: 

  - Android Studio 1.0.2 or above
  - Android SDK 4.0 (API Level 14) or above
  - Gradle???
  - [Liferay Portal 6.2 CE or EE](http://www.liferay.com/downloads/liferay-portal/available-releases)
  - [Liferay Screens' compatiblity plugin](https://github.com/liferay/liferay-screens/tree/master/portal). 
  - Liferay Screens Java source code


## Compatibility

This implementation of Liferay Screens is compatible from Android 4.0 (API Level 14). 
TODO: Mention compat library???

## Preparing Your Project for Liferay Screens

Liferay Screens is released as [AAR file](http://tools.android.com/tech-docs/new-build-system/aar-format) published in Maven Central repository. 

So you need to use Maven or Gradle to set amd download your dependencies.

Next you can find the steps to perform to configure your project with Gradle:

1. XXX
2. XXX
3. XXX

Great! Your project should now be ready for Liferay Screens. Next, you'll learn how to use screenlets in your own app.

## Using Screenlets

Now you're ready to start using screenlets in your project. First, insert a screenlet in your activity's or fragment's layout. You can use the XML editor or the Android Studio's visual one:

![Add UIWindow](Documentation/Images/add-uiwindow.png "Add UIWindow")

Next, set the properties of the screenlet in the XML tag:

![Change Custom Class](Documentation/Images/custom-class.png "Change Custom Class")

Now you need to listener the events produced by the screenlet. For this, implement the xxxListener interface in your Activity or Fragment class. For instance, for the `LoginScreenlet` you need to implement the interface `LoginListener`.

![Conform delegate](Documentation/Images/conform-delegate.png "Conform delegate")

And finally, set your activity or fragment class as screenlet's listener:

![Connect delegate in Interface Builder](Documentation/Images/xcode-delegate.png "Connect delegate in Interface Builder")

Awesome! Now you know how to use screenlets in your app.

## Listing of Available Screenlets

Screenlets are grouped in modules based on internal dependencies. Each module is isolated, so you can use only the modules that are necessary for your project. However, it's important to note that you can't use a screenlet from a single module without using the entire module. The screenlets here are listed according to the module that they belong to.

- **Auth**: Module for user authentication. It uses the [user management](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/user-management) features of Liferay Portal. It includes the following screenlets:

	- [`LoginScreenlet`](documentation/LoginScreenlet.md): Gives your app the ability to sign users in to a Liferay instance.
	- [`SignUpScreenlet`](documentation/SignUpScreenlet.md): Gives your app the ability to sign new users in to a Liferay instance.
	- [`ForgotPasswordScreenlet`](documentation/ForgotPasswordScreenlet.md): Gives your app the ability to send emails containing a new password or password reset link to users.
	- [`UserPortraitScreenlet`](documentation/UserPortraitScreenlet.md): Shows the user's portrait picture.

- **Dynamic Data Lists (DDL)**: Module for interacting with [Dynamic Data Lists](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/using-web-forms-and-dynamic-data-lists) in a Liferay instance. It includes the following screenlet:

	- [`DDLFormScreenlet`](documentation/DDLFormScreenlet.md): Gives your app the ability to present dynamic forms to be filled by users and submitted back to the server.
	- [`DDLListScreenlet`](documentation/DDLListScreenlet.md): Gives your app the ability to show a list of records based on a pre-existing DDL in a Liferay instance.

Also, some screenlets can be used individually without the need to import an entire module. These include:

- [`AssetListScreenlet`](documentation/AssetListScreenlet.md): Shows a list of assets managed by [Liferay's Asset Framework](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en). This includes web content, blog entries, documents, users and more.
- [`WebContentDisplayScreenlet`](Documentation/WebContentDisplayScreenlet.md): Shows the HTML of web content. This screenlet uses the features avaiable in [Web Content Management](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/web-content-management).

## Listing of Available Views

With Views sets, you can control the look and feel of any screenlet that you decide to use in you app. What's more, these views are fully pluggable. You can change the UI and UX of your app by installing new view sets to extend and customize any screenlet.

The view sets currently released with Liferay Screens are:

- **Default**: The standard view set that is used when you include any screenlet on your activity or fragment and don't change de values of `liferay:layoutId` attribute. It can be used as the parent view for any of your custom views (refer to the [Architecture Guide](Documentation/architecture.md#view-layer) for more details on this).
- **Material**: A sample view set intended to demonstrate how to develop your own full views from scratch. For information on creating your own view set, refer to the [Views Guide](Documentation/views.md).

## Contributing New Screenlets and Views

If you have a piece of code that can be reused in other apps, you may want to contribute it to the Liferay Screens project. Doing so is very straightforward: just follow the instructions in the [Contributors Guide](https://github.com/liferay/liferay-screens/tree/master/CONTRIBUTING.md).

