# Liferay Mobile Widgets for iOS

## Important note
__This product is under heavy development and most of the features aren't ready to be used in production.
It's public just in order to allow developers to preview the technology.__

## Introduction
This is the iOS implementation for Liferay Widgets.

It includes the framework implementation itself and two sample projects: one for using widgets from Objective-C and another from Swift.

Note that the implementation uses Swift language and iOS 8 SDK, so there's a lot of issues and workarounds present at the moment. As soon as the SDK becomes more stable, the workarounds will be removed.

For more details of the inner implementation and architecture, check the framework documentation page.


## How to use a widget in your project
If you want to use an existing widget, follow next steps

### Initial setup
We are assuming that you already have an XCode project already configured and have basic knowledge of iOS development.

1. Download [Liferay Widget's source code](https://github.com/liferay/liferay-mobile-widgets/archive/master.zip) and include it in your project:
	1. Create a folder  at the root of the project called `Liferay-Widget-Library`
	1. Copy inside the folder `Library/Source` from the downloaded source code
	1. Drag and drop `Liferay-Widget-Library` into your project in XCode
1. If you don't have it yet, set up [cocoapods](http://cocoapods.org) for your project
1. Add the following dependencies to your Podfile and execute `pod install`
	- pod 'MBProgressHUD'
	- pod 'Liferay-iOS-SDK'
1. Edit the following Builder Settings in your target:
    - Objective-C Bridging Header: `${SRCROOT}/Liferay-Widget-Library/Source/liferay-ios-widgets.h`
1. Create a new file of type Property List called `liferay-context.plist` to configure the settings for your Liferay Portal (like the URL to the server). Use [`liferay-context-sample.plist`](https://github.com/liferay/liferay-mobile-widgets/raw/master/ios/framework/liferay-ios-widgets/liferay-context-sample.plist) as a template.

### Adding a widget

1. Using Interface Builder, insert a new UIView in your Storyboard or XIB file.
1. Change the Custom Class to widget's class name. For example: `LoginWidget`.
1. In your ViewController class adopt widget's delegate protocol in your view controller. For example: `LoginWidgetDelegate`
2. Go back to Interface Builder and set widget's delegate to your view controller. If the specific widget has more outlets, you may assign them too.

### Additional steps for Objective-C code

In order to invoke widget classes from your Objective-C code, import the following header files:

    #import "liferay-ios-widgets.h"
    #import "[name_of_your_project]-Swift.h"
    
If your project's name uses non-alphanumeric characters, replace them by _

And if you get bored of adding the same imports over and over again, you may add a precompiler header file following these steps (optional):

1. Create and add to your project the file `Prefix.pch`
1. Add to that file previous imports
1. Edit Build Settings of you target
    - Precompile Prefix Header: Yes
    - Prefix Header: path_to_your_file_Prefix.pch

## How to contribute new widgets

You would like to contribute new widgets? Awesome :)

You have all the information in [/liferay/liferay-mobile-widgets/tree/master/ios/Library/README.md]