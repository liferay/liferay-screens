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

###To setup your project

1. Download framework source code and include it in your project (drop folder `$(widgets_ios_src)/framework/liferay-ios-widgets/liferay-ios-widgets` from widget's source code to your xcode project)
1. Add next dependencies to your Podfile
      - MBProgressHUD
      - Liferay-iOS-SDK
1. Edit next Builder Settings in your target:
    - Objective-C Bridging Header: path to `$(widgets_ios_src)/framework/liferay-ios-widgets/liferay-ios-widgets.h`
1. Create and add to your project the file `liferay-context.plist`. It will describe the settings for your Liferay Portal (like server's url). Use `liferay-context-sample.plist` as a template.

###To use a widget in your project

1. Using Interface Builder, insert a new UIView in your Storyboard or XIB file.
1. Change the Custom Class to widget's class name (`FooWidget`)
1. Adopt widget's delegate protocol (`FooWidgetDelegate`) in your view controller.
2. In Interface Builder set widget's delegate to your view controller. If the specific widget has more outlets, you may assign them too.

When you need to invoke widget classes from your Objective-C code, you have to import next header files:

    #import "liferay-ios-widgets.h"
    #import "[name_of_your_project]-Swift.h"
    
If your project's name uses non-alphanumeric characters, replace them by _

And if you get bored of add same imports over and over again, you may add a precompiler header file following next steps (optional):

1. Create and add to your project the file `Prefix.pch`
1. Add to that file previous imports
1. Edit Build Settings of you target
    - Precompile Prefix Header: Yes
    - Prefix Header: path_to_your_file_Prefix.pch




## How to contribute new widgets
If you want to develop one new widget, go to framework documentation page.