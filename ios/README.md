# Liferay Screens for iOS

## Important note

__This product is under heavy development and its features aren't ready for use 
in production. It's being made public only to allow developers to preview the 
technology.__

## Introduction

The iOS implementation for Liferay Screens includes the widget library and two 
sample projects. One sample project uses Screens from Objective-C, while the 
other uses Screens from Swift.

In Liferay Screens, a *widget* is a visual component that is connected to 
Liferay Portal's functionality. The widget is responsible for handling 
communication between the server and the UI. Widgets also implement all typical 
[human interface guidelines described by Apple](https://developer.apple.com/library/ios/documentation/userexperience/conceptual/mobilehig/).

Each widget is tied to one or more services exposed by 
[Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en). 
The widget then renders information using a theme. Themes can be contributed by 
third parties and are fully pluggable, so you're not limited to one specific 
look and feel. The Screens library provides a standard theme called *Default* 
and another sample theme called *Flat7*.

![The SignUp widget using Default and Flat7 themes](Documentation/Images/signup.png)

Please note that themes provided in early versions support only vertical screen 
orientation and the iPhone 5, 5s, and 5c screen size. Support for the full range 
of screen orientations and sizes will be added in the future.

To learn more detail about the architecture of Screens, please see the 
[library documentation page](https://github.com/liferay/liferay-screens/tree/master/ios/Library/README.md).

## Requirements

Development of iOS apps using Liferay Screens requires the following: 

  - XCode 6.0 or above
  - iOS 8 SDK
  - [CocoaPods](http://cocoapods.org) installed
  - [Liferay Portal 6.2 CE or EE](http://www.liferay.com/downloads/liferay-portal/available-releases)
  - [Mobile Widgets plugin for 6.2.x](https://github.com/liferay/liferay-plugins/tree/6.2.x/webs/mobile-widgets-web). 
    This plugin will be available soon in the [Liferay Marketplace](https://www.liferay.com/marketplace).
  - Liferay Screens source code

Your iOS app can we written in Swift or Objective-C.

## Compatibility 

This implementation of Liferay Screens uses the Swift language. However, it 
doesn't use the new iOS 8 APIs, so it can be run only on devices with iOS 7 and 
above.

## Preparing Your Project for Liferay Screens

There are a few things you need to do with your XCode project to prepare it for 
Liferay Screens. As soon as CocoaPods supports Swift libraries, you can prepare 
your project by simply adding one new line to your `Podfile`. Until then, you 
need to manually install Liferay Screens in your project.

First, you need to download the [Liferay Screens source code](https://github.com/liferay/liferay-screens/archive/master.zip) 
and add it to your project. The steps for doing this are shown here:

1. Create a folder at the root of the project called `Liferay-Screens`.
2. Copy the folders `Library/Source` and `Library/Themes` from the downloaded 
   source code into this new folder.
3. Drag `Liferay-Screens` from the Finder and drop it into your XCode project.

![This XCode project has Liferay Screens.](Documentation/Images/project-setup.png)

Next, set up [CocoaPods](http://cocoapods.org) for your project if you haven't 
done so already. Add the dependencies to your `Podfile` and then execute 
`pod install`. Use this [`Podfile`](https://github.com/liferay/liferay-screens/tree/master/ios/Library/Podfile) 
as a template.

You also need to edit the following Builder Settings in your project's 
configuration:

    Objective-C Bridging Header: `${SRCROOT}/Liferay-Screens/Source/liferay-ios-widgets.h`

![Objective-C Bridging Header](Documentation/Images/project-header.png)

Create a new property list file called `liferay-server-context.plist` to 
configure the settings for your Liferay Portal instance. Use 
[`liferay-server-context-sample.plist`](https://github.com/liferay/liferay-screens/tree/master/ios/Library/Source/liferay-server-context-sample.plist) 
as a template.

![liferay-context.plist file](Documentation/Images/liferay-context.png "liferay-context.plist file")

Great! Now your project should be all ready for Liferay Screens. Next you'll 
learn how to use widets in your project.

## Using Widgets

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

	- [DDLForm](): it gives your app the capacity to present dynamic forms, be filled by users and submitted back to the server
	- [DDLList](): it gives your app the capacity to show a list of records based on DDL previously submitted by forms (web or app based)

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
