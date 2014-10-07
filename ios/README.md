# Liferay Screens for iOS

## Important Note

_This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology._

## Introduction

The iOS implementation for Liferay Screens includes the widget library and two sample projects. One sample project uses Screens from Objective-C, while the other uses Screens from Swift.

In Liferay Screens, a *widget* is a visual component that is connected to Liferay Portal's functionality. The widget is responsible for handling communication between the server and the UI. Widgets also implement all typical [human interface guidelines described by Apple](https://developer.apple.com/library/ios/documentation/userexperience/conceptual/mobilehig/).

Each widget is tied to one or more services exposed by [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en).  The widget then renders information using a theme. Themes can be contributed by third parties and are fully pluggable, so you're not limited to one specific look and feel. The Screens library provides a standard theme called *Default*  and another sample theme called *Flat7*.

![The SignUp widget using Default and Flat7 themes](Documentation/Images/signup.png)

Please note that themes provided in early versions support only vertical screen orientation and the iPhone 5, 5s, and 5c screen size. Support for the full range  of screen orientations and sizes will be added in the future.

To learn more detail about the architecture of Screens, please see the [library documentation page](https://github.com/liferay/liferay-screens/tree/master/ios/Library/README.md).

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

This implementation of Liferay Screens uses the Swift programming language. However, it doesn't use the new iOS 8 APIs, so it can be run only on devices with iOS 7 and above.

## Preparing Your Project for Liferay Screens

There are a few things you need to do with your XCode project to prepare it for Liferay Screens. As soon as CocoaPods supports Swift libraries, you can prepare your project by simply adding one new line to your `Podfile`. Until then, you need to manually install Liferay Screens in your project.

First, you need to download the [Liferay Screens source code](https://github.com/liferay/liferay-screens/archive/master.zip) and add it to your project. The steps for doing this are shown here:

1. Create a folder at the root of the project called `Liferay-Screens`.
2. Copy the folders `Library/Source` and `Library/Themes` from the downloaded 
   source code into this new folder.
3. Drag `Liferay-Screens` from the Finder and drop it into your XCode project.

![This XCode project has Liferay Screens.](Documentation/Images/project-setup.png)

Next, set up [CocoaPods](http://cocoapods.org) for your project if you haven't done so already. Add the dependencies to your `Podfile` and then execute `pod install`. Use this [Podfile](https://github.com/liferay/liferay-screens/tree/master/ios/Library/Podfile) as a template.    

In your project's build settings, you also need to edit the Objective-C Bridging Header to include `${SRCROOT}/Liferay-Screens/Source/liferay-ios-widgets.h`. This is shown in the following screenshot:

![Objective-C Bridging Header](Documentation/Images/project-header.png)

There's just one more thing to take care of to ensure that your project is ready for Liferay Screens. Create a new property list file called `liferay-server-context.plist`. You'll use this file to configure the settings for your Liferay Portal instance. Use [`liferay-server-context-sample.plist`](https://github.com/liferay/liferay-screens/tree/master/ios/Library/Source/liferay-server-context-sample.plist) as a template. This screenshot shows such a file being browsed:

![liferay-context.plist file](Documentation/Images/liferay-context.png)

Great! Your project should now be ready for Liferay Screens. Next, you'll learn how to use widgets in your project.

## Using Widgets

Now you're ready to start using widgets in your project. First, use Interface Builder to insert a new UIView in your Storyboard or XIB file. This is shown in the following screenshot:

![Add UIWindow](Documentation/Images/add-uiwindow.png "Add UIWindow")

Next, change the Custom Class to widget's class name. For example, if you're using `LoginWidget`, then enter that as the Custom Class name. This is shown here:

![Change Custom Class](Documentation/Images/custom-class.png "Change Custom Class")

Now you need to conform the widget's delegate protocol in your `ViewController` class. For example, for the `LoginWidget` this is `LoginWidgetDelegate`.
<!-- 
Is "conform" supposed to be "confirm" here? Also, do you want to use a 
screenshot or some other code sample following the text? - Nick
-->

Now that the widget's delegate protocol is set in your `ViewController` class, go back to Interface Builder and set the widget's delegate to your view controller. If the widget you're using has more outlets, you can assign them as well.
<!-- 
Screenshot or code sample?
- Nick
-->

Awesome! Now you know how to use widgets in your projects. However, if you want to use widgets from Objective-C code, there are a few more things that you need to take care of. These are presented in the next section. If you don't need to use widgets from Objective-C, you can skip this section and proceed to the list of available widgets below.

### Using Widgets from Objective-C Code

If you want to invoke widget classes from your Objective-C code, then there are a couple of additional header files that you need to import. Their import statements are shown here:

    #import "liferay-ios-widgets.h"
    #import "[name_of_your_project]-Swift.h"
    
Simply replace `name_of_your_project` with your project's name. If your project's name uses non-alphanumeric characters, replace them with `_`. If you get tired of adding the same imports over and over again, you can add a precompiler header file using the following steps:

1. Create the file `Prefix.pch` and add it to your project.
2. Add the previous imports to that file.
3. Edit the following build settings of your target, using the indicated settings. Remember to replace `path\_to\_your\_file\` with the path to your `Prefix.pch` file:

    - Precompile Prefix Header: `Yes`
    - Prefix Header: `path\_to\_your\_file\_Prefix.pch`

Super! Now you know how to call widgets from the Objective-C code in your project. Next, a list of the widgets available in Liferay Screens is presented.
    
## Listing of Available Widgets

Widgets are grouped in modules based on internal dependencies. Each module is isolated, so you can use only the modules that are neccesary for your project. However, it's important to note that you can't use a widget from a single module without using the entire module. The widgets here are listed according to the module that they belong to.

- **Auth**: Module for user authentication. It uses the [user management](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/management-liferay-portal-6-2-user-guide-16-en) features of Liferay Portal. It includes the following widgets:

	- [`LoginWidget`](Documentation/LoginWidget.md): Gives your app the ability to sign users in to a Liferay instance.
	- [`SignUpWidget`](Documentation/SignUpWidget.md): Gives your app the ability to sign new users in to a Liferay instance.
	- [`ForgotPasswordWidget`](Documentation/ForgotPasswordWidget.md): Gives your app the ability to send emails containing a new password or password reset link to users.

- **Dynamic Data Lists (DDL)**: Module for interacting with [Dynamic Data Lists](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/using-web-forms-and-dynamic-data-lists-liferay-portal-6-2-user-guide-10-en) in a Liferay instance. It includes the following widgets:

	- [`DDLFormWidget`](Documentation/DDLFormWidget.md): Gives your app the ability to present dynamic forms to be filled by users and submitted back to the server.
	- [`DDLListWidget`](Documentation/DDLListWidget.md): Gives your app the ability to show a list of records based on a pre-existing DDL in a Liferay instance.

Also, some widgets can be used individually without the need to import an entire module. These include:

- [`AssetListWidget`](Documentation/AssetListWidget.md): Shows a list of assets managed by [Liferay's Asset Framework](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en). This includes users, organizations, sites, and more.
- [`WebContentDisplayWidget`](Documentation/WebContentDisplayWidget.md): Shows the HTML of web content. This widget uses the features avaiable in [Web Content Management](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/web-content-management-liferay-portal-6-2-user-guide-02-en).

Liferay Screens also contains themes that you can use to style widgets. A list of these themes is presented next.
	  
## Listing of Available Themes

With themes, you can control the look and feel of any widgets that you decide to use in Liferay Screens. What's more, these themes are fully pluggable. You can install new themes to extend and customize widgets to meet the design and UX of your app.

The themes currently released with Liferay Screens are:

  - **Default**: The standard theme that is used when you insert a widget in your screen.
  - **Flat7**: A sample theme intended to demostrate how to develop your own theme from scratch.

## Contributing New Widgets and Themes

If you have a piece of code that can be reused in other apps, you may want to contribute it to the Liferay Screens project. Doing so is very straightforward: just follow the instructions in [Contributors Guide](https://github.com/liferay/liferay-screens/tree/master/CONTRIBUTING.md).
<!-- 
Some kind of conclusion or related links/next steps is needed.
- Nick
-->
