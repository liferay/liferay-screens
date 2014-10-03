# Liferay Screens for iOS

## Important note
__This product is under heavy development and most of the features aren't ready to be used in production.
It's public just in order to allow developers to preview the technology.__

## Introduction
This is the iOS implementation for Liferay Screens.

It includes the widgets library and two sample projects: one to use Screens from Objective-C and another from Swift.

In Liferay Screens, we call "widget" to a visual component connected to Liferay's portal funcionallity. It's responsible of communicating with the server (Liferay Portal), and to get/show the information from/to the UI. Widgets also implements all typical [human interface guideliness described by Apple](https://developer.apple.com/library/ios/documentation/userexperience/conceptual/mobilehig/).

Each widget is tied to one or more service exposed by [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en), and renders the information using one component called "theme". Themes can be contributed be third parties and are 100% plugable, so you're not limited to one specific look and feel.
Screens library provides one standard theme called "Default" and another sample one called "Flat7"

![SignUp widget using Default and Flat7 themes](Documentation/Images/signup.png "SignUp widget using Default theme")

Notice that themes provided in early versions supports only iPhone 5 and vertical resolution, but they will eventually support the wide range of screens and resolutions.

For more details of the internal architecture, check the [library documentation page](https://github.com/liferay/liferay-screens/tree/master/ios/Library/README.md)


## Requirements
In order to develop iOS apps using Liferay Screen, you will need:
  - XCode 6.0 or above
  - iOS 8 SDK
  - [CocoaPods](http://cocoapods.org) installed
  - [Liferay Portal 6.2 CE or EE](http://www.liferay.com/downloads/liferay-portal/available-releases)
  - [Mobile Widgets plugin for 6.2.x](https://github.com/liferay/liferay-plugins/tree/6.2.x/webs/mobile-widgets-web). This plugin will be available soon in the [Liferay Marketplace](https://www.liferay.com/marketplace)
  - Liferay Screen source code

Your iOS app can we written both in Swift or Objective-C

## Compatibility 
This implementation uses Swift language, but it doesn't use new iOS 8 APIs, so it can be run in any device with iOS 7 and above.


## How to prepare your project to use Liferay Screens

We are assuming that you have an XCode project already configured and have basic knowledge of iOS development.

Notice these steps are the manual way to install Liferay Screens in your project. As soon as CocoaPods supports Swift libraries, it will be as easy as add one new line to your `Podfile`

1. Download [Liferay Screens's source code](https://github.com/liferay/liferay-screens/archive/master.zip) and add it to your project:
	1. Create a folder at the root of the project called `Liferay-Screens`.
	1. Copy folders `Library/Source` and `Library/Themes` from the downloaded source code into this new folder.
	1. Drag `Liferay-Screens` from the Finder and drop it into your XCode project.

	![XCode project with Liferay Screens](Documentation/Images/project-setup.png "XCode project with Liferay Screens")


1. If you didn't do it yet, set up [CocoaPods](http://cocoapods.org) for your project
1. Add depencendies to your `Podfile` and execute `pod install`. Use this [`Podfile`](https://github.com/liferay/liferay-screens/tree/master/ios/Library/Podfile) as a template.
1. Edit the following Builder Settings in your project's configuration:
    - Objective-C Bridging Header: `${SRCROOT}/Liferay-Screens/Source/liferay-ios-widgets.h`

	![Objective-C Bridging Header](Documentation/Images/project-header.png "Objective-C Bridging Header")

1. Create a new file of type Property List called `liferay-server-context.plist` to configure the settings for your Liferay Portal (like the URL to the server). Use [`liferay-server-context-sample.plist`](https://github.com/liferay/liferay-screens/tree/master/ios/Library/Source/liferay-server-context-sample.plist) as a template.

	![liferay-context.plist file](Documentation/Images/liferay-context.png "liferay-context.plist file")


## How to use a widget in your project

1. Using Interface Builder, insert a new UIView in your Storyboard or XIB file.

	![Add UIWindow](Documentation/Images/add-uiwindow.png "Add UIWindow")

1. Change the Custom Class to widget's class name. For example: `LoginWidget`.

	![Change Custom Class](Documentation/Images/custom-class.png "Change Custom Class")

1. In your ViewController class conform widget's delegate protocol in your view controller. For example: `LoginWidgetDelegate`

1. Go back to Interface Builder and set widget's delegate to your view controller. If the specific widget has more outlets, you may assign them too.

#### Additional steps for Objective-C code

In order to invoke widget classes from your Objective-C code, import the following header files:

    #import "liferay-ios-widgets.h"
    #import "[name_of_your_project]-Swift.h"
    
If your project's name uses non-alphanumeric characters, replace them by _

And if you get bored of adding the same imports over and over again, you may add a precompiler header file following these steps (optional):

1. Create and add to your project the file `Prefix.pch`
1. Add previous imports to that file
1. Edit Build Settings of you target
    - Precompile Prefix Header: Yes
    - Prefix Header: path\_to\_your\_file\_Prefix.pch

## List of available widgets

Widgets are grouped in modules based on its internal dependencies. Each module is isolated, so you can use the modules that are neccesary in your project. You cannot use one widget from one module without using the whole module.

Modules:

- **Auth**: all related widgets with user authentication. It uses the [user management](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/management-liferay-portal-6-2-user-guide-16-en) features of your Liferay Portal. It includes:

	- [LoginWidget](Documentation/LoginWidget.md): gives your app the capacity to sign in users into it.
	- [SignUpWidget](Documentation/SignUpWidget.md): gives your app the capacity to sign up users into it.
	- [ForgotPasswordWidget](Documentation/ForgotPasswordWidget.md): gives your app the capacity to send emails to users with new password or reset link.

- **Dynamic Data Lists (DDL)**: all widgets that allow to interact with dynamically created data forms and records. It uses all the features available from [Dynamic Data List](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/using-web-forms-and-dynamic-data-lists-liferay-portal-6-2-user-guide-10-en) of your portal.

	- [DDLForm](Documentation/DDLFormWidget.md): it gives your app the capacity to present dynamic forms, be filled by users and submitted back to the server
	- [DDLList](Documentation/DDLListWidget.md): it gives your app the capacity to show a list of records based on DDL previously submitted by forms (web or app based)

- **Other**: this widgets could be used individually without importing the whole module. It includes

	- [AssetListWidget](): it shows a list of asset managed by [Liferay Portal's Asset Framework](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en). It includes users, organizations, groups (sites), etc.
	- [WebContentDisplayWidget](): it shows the HTML content of a web content. It uses the features avaiable from [Web Content Management](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/web-content-management-liferay-portal-6-2-user-guide-02-en)

## List of available themes

Together with themes, we release set of themes that implement the Look and feel of the widgets.
Themes are plugable, you can install new themes to extend and customize widgets to meet your app design and UX.

Themes currently released are:
  - **Default**: this is the standard theme that is used when you insert a widget in your screen.
  - **Flat7**: it's a sample theme intended to demostrate how to develop your own theme from scratch.

## How to contribute new widgets

Do you have a piece of code that can be reused in other apps? Awesome :)

It's so simple: just follow our [contributors guide](https://github.com/liferay/liferay-screens/tree/master/CONTRIBUTING.md)

## How to contribute new themes

Do you have a design implemented that could be reused in other apps? Awesome :)

It's so simple: just follow our [contributors guide](https://github.com/liferay/liferay-screens/tree/master/CONTRIBUTING.md)
