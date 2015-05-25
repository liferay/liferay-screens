# Liferay Screens for iOS Manual Installation Guide

## Introduction

This guide explains how to install Liferay Screens into your project without using [CocoaPods](http://cocoapods.org). Also, you must use this procedure if you want your app to support iOS 7. The reasons for this are detailed in [this CocoaPods article](http://blog.cocoapods.org/Pod-Authors-Guide-to-CocoaPods-Frameworks/).

## Requirements

To install Liferay Screens manually in your Xcode project, you need the following: 

- Xcode 6.3 or above
- iOS 8 SDK
- [CocoaPods](http://cocoapods.org) installed
- [Liferay Portal 6.2 CE or EE](http://www.liferay.com/downloads/liferay-portal/available-releases)
- [Liferay Screens' compatiblity plugin](https://github.com/liferay/liferay-screens/tree/master/portal). 
- Liferay Screens source code

## Preparing Your Project for Liferay Screens

There are a few things you need to manually setup in your iOS 7 app to prepare it for Liferay Screens. First, you need to download the [Liferay Screens source code](https://github.com/liferay/liferay-screens/releases) and include it in your project. The steps for doing this are shown here:

1. Create a folder at the root of your project called `Liferay-Screens`.
2. Copy the folders `Library/Core` and `Library/Themes` from the downloaded 
   source code into this new folder. After this, you'll have two subdirectories inside of your project's `Liferay-Screens` directory: `Core` and `Themes`.
3. Drag `Liferay-Screens` from the Finder and drop it into your Xcode project.

![This Xcode project includes Liferay Screens.](Images/project-setup.png)

Next, set up [CocoaPods](http://cocoapods.org) for your project if you haven't done so already. Add the following dependencies to your `Podfile` and then execute `pod install`. 

```ruby
source 'https://github.com/CocoaPods/Specs.git'

platform :ios, '7.0'

pod 'MBProgressHUD'
pod 'SMXMLDocument'
pod 'UICKeyChainStore'
pod 'DTPickerPresenter'
pod 'TNRadioButtonGroup'
pod 'MDRadialProgress'
pod 'ODRefreshControl'
pod 'Liferay-iOS-SDK'
```

You should consider using the [CocoaPods for Xcode plugin](https://github.com/kattrali/cocoapods-xcode-plugin). You can install it through the [Alcatraz package manager](http://alcatraz.io/)) for Xcode. This way, you can perform these tasks from Xcode. 

![The CocoaPods for Xcode plugin.](Images/xcode-cocoapods.png)

If you want to add Liferay Screens to your test target (to be used under XCTest), then you need to use a `Podfile` similar to this one:

```ruby
source 'https://github.com/CocoaPods/Specs.git'

platform :ios, '7.0'

def import_pods
	pod 'MBProgressHUD'
	pod 'SMXMLDocument'
	pod 'UICKeyChainStore'
	pod 'DTPickerPresenter'
	pod 'TNRadioButtonGroup'
	pod 'MDRadialProgress'
	pod 'ODRefreshControl'
	pod 'Liferay-iOS-SDK'
end

import_pods

target :<<You Test Target Name Goes Here>> do
	import_pods
end
```

In your project's build settings, you also need to edit the *Objective-C Bridging* Header to include `${SRCROOT}/Liferay-Screens/Core/liferay-screens-bridge.h`. This is shown in the following screenshot:

![Objective-C Bridging Header](Images/project-header.png)

There's just one more thing to take care of to ensure that your project is ready for Liferay Screens. Create a new property list (`.plist`) file called `liferay-server-context.plist`. You'll use this file to configure the settings for your Liferay Portal instance. Use [`liferay-server-context-sample.plist`](https://github.com/liferay/liferay-screens/tree/master/ios/Framework/Core/Resources/liferay-server-context-sample.plist) as a template. This screenshot shows such a file being browsed:

![A `liferay-context.plist` file.](Images/liferay-context.png)

Great! Your project should now be ready for Liferay Screens.

## Troubleshooting

Since the preferred way to install Liferay Screens is using CocoaPods, you should be aware that there are some problems associated with the manual installation procedure. One such problem is the 'Unknown Class in Interface Builder' file error. This is shown in the following screenshot:

![Unknown class X in Interface Builder file exception](Images/xcode-unknown-class.png)

This error occurs when you use a screenlet with Liferay Screens installed manually. It exists because the screenlet views are bound to the `LiferayScreens` Module, which only exists if you installed Screens with CocoaPods. To solve this problem you must open the failed `xib` file listed in the error trace (in the above screenshot this is `LoginView_default`), select the root view, and then re-set the Custom class. Note the Module value should change from `LiferayScreens` to the grayed out text `Current - your app name`.

Before and after screenshots of the cutom class assignment are shown here:

![Unknown class X in Interface Builder file exception](Images/xcode-custom-class-before.png)

![Unknown class X in Interface Builder file exception](Images/xcode-custom-class-after.png)
