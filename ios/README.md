# Liferay Screens for iOS

## Introduction

The iOS implementation for Liferay Screens includes the components (*screenlets*) library and three sample projects. One sample project uses Screens from Objective-C, while the other two use Screens from Swift.

In Liferay Screens, a screenlet is a visual component that is connected to Liferay Portal's functionality. The screenlet is responsible for handling communication between the server and the UI. Screenlets also implement all typical [human interface guidelines described by Apple](https://developer.apple.com/library/ios/documentation/userexperience/conceptual/mobilehig/).

Each screenlet is tied to one or more services exposed by [Liferay's remote services](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/accessing-services-remotely-liferay-portal-6-2-dev-guide-05-en). The screenlet then renders information using a theme. Themes can be contributed by third parties and are fully pluggable, so you're not limited to one specific look and feel. The Screens library provides a standard theme called *Default*  and two other sample themes called *Flat7* and *Westeros*.

![The SignUp screenlet using Default and Flat7 themes](Documentation/Images/screens-phone2.png)

Please note that themes provided in early versions support only vertical screen orientation and the iPhone 5, 5s, and 5c screen size. Support for the full range of screen orientations and sizes will be added in the future.

To learn more detail about the architecture of Screens, please see the [library documentation page](https://github.com/liferay/liferay-screens/tree/master/ios/Framework/README.md).

## Requirements

Development of iOS apps using Liferay Screens requires the following: 

  - Xcode 6.3 or above
  - iOS 8 SDK
  - [CocoaPods](http://cocoapods.org) installed
  - [Liferay Portal 6.2 CE or EE](http://www.liferay.com/downloads/liferay-portal/available-releases)
  - [Liferay Screens' compatiblity plugin](https://github.com/liferay/liferay-screens/tree/master/portal). 
  - Liferay Screens source code

Your iOS app can we written in Swift or Objective-C.

## Compatibility

This implementation of Liferay Screens uses the Swift programming language. However, it doesn't use the new iOS 8 APIs, so it can be run on any device with iOS 7 and above.

## Preparing Your Project for Liferay Screens

Liferay Screens is released as a standard [CocoaPods](https://cocoapods.org/) dependency, so you just need to add the line `pod 'LiferayScreens'` in your project's `Podfile`.

However, since Liferay Screens is written in Swift, you need to use CocoaPods version 0.36 or above. Also, this only works on iOS 8.0 or above. Refer to [this article](http://blog.cocoapods.org/CocoaPods-0.36/) for more details about how CocoaPods works with Swift dependencies.

Your final `Podfile` should be similar to this one:

```ruby
source 'https://github.com/CocoaPods/Specs.git'

platform :ios, '8.0'
use_frameworks!

pod 'LiferayScreens'

# the rest of your Podfile
```

You can use this [Podfile](https://github.com/liferay/liferay-screens/tree/master/ios/Samples/Showcase-swift/Podfile) as a template.

If you want to support iOS 7, you need to [manually install the library into your project.](Documentation/ManualInstallation.md)

There's just one last step to ensure that your project is ready for Liferay Screens. Create a new property list (`.plist`) file called `liferay-server-context.plist`. You'll use this file to configure the settings for your Liferay Portal instance. Use [`liferay-server-context-sample.plist`](https://github.com/liferay/liferay-screens/tree/master/ios/Framework/Core/Resources/liferay-server-context-sample.plist) as a template. This screenshot shows such a file being browsed:

![A `liferay-context.plist` file.](Documentation/Images/liferay-context.png)

Great! Your project should now be ready for Liferay Screens. Next, you'll learn how to use screenlets in your project.

## Using Screenlets

Now you're ready to start using screenlets in your project. First, use Interface Builder to insert a new UIView in your Storyboard or XIB file. This is shown in the following screenshot:

![Add UIWindow](Documentation/Images/add-uiwindow.png "Add UIWindow")

Next, change the Custom Class to screenlet's class name. For example, if you're using `LoginScreenlet`, then enter that as the Custom Class name. This is shown here:

![Change Custom Class](Documentation/Images/custom-class.png "Change Custom Class")

Now you need to conform the screenlet's delegate protocol in your `ViewController` class. For example, for the `LoginScreenlet` this is `LoginScreenletDelegate`.

![Conform delegate](Documentation/Images/conform-delegate.png "Conform delegate")

Now that the screenlet's delegate protocol is conformed in your `ViewController` class, go back to Interface Builder and connect the screenlet's delegate to your view controller. If the screenlet you're using has more outlets, you can assign them as well.

![Connect delegate in Interface Builder](Documentation/Images/xcode-delegate.png "Connect delegate in Interface Builder")

*Note that there are [some issues](http://stackoverflow.com/questions/26180268/interface-builder-iboutlet-and-protocols-for-delegate-and-datasource-in-swift/26180481#26180481) connecting an outlet to Swift source code. You can change the delegate data type or just assign the outlets by code.*

Awesome! Now you know how to use screenlets in your projects. However, if you want to use screenlets from Objective-C code, there are a few more things that you need to take care of. These are presented in the next section. If you don't need to use screenlets from Objective-C, you can skip this section and proceed to the list of available screenlets below.


### Screenlet Localization

You can localize screenlets to show their information in different languages. This uses [Apple's standard mechanism](https://developer.apple.com/library/ios/documentation/MacOSX/Conceptual/BPInternational/Introduction/Introduction.html) for localization.

Even though the screenlets support several languages, you have to support those languages in your app. In other words, if you don't support a given language in your app, the screenlet doesn't support that language either. To support a language, make sure that your project's settings list the language, as in the following screenshot:

![Xcode localizations in the project's settings.](Documentation/Images/xcode-localizations.png "Xcode localizations in project's settings")

### Using Screenlets from Objective-C Code

If you want to invoke screenlet classes from your Objective-C code, then there is an additional header file that you need to import in every Objective-C file:

```Objective-C
#import "LiferayScreens-Swift.h"
```
To avoid adding the same imports over and over again, you can add a precompiler header file by using the following steps:

1. Create the file `PrefixHeader.pch` and add it to your project.

2. Add the previous import to that file.

3. Edit the following build settings of your target, using the indicated settings. Remember to replace `path/to/your/file/` with the path to your `PrefixHeader.pch` file:

    - Precompile Prefix Header: `Yes`
    - Prefix Header: `path/to/your/file/PrefixHeader.pch`

    ![The `PrefixHeader.pch` configuration in Xcode settings.](Documentation/Images/xcode-prefix.png)

You can use this [`PrefixHeader.pch`](https://github.com/liferay/liferay-screens/tree/master/ios/Samples/Showcase-objc/LiferayScreens-Showcase-Objc/PrefixHeader.pch) as a template.
 
Super! Now you know how to call screenlets from the Objective-C code in your project. Next, a list of the screenlets available in Liferay Screens is presented.

## Listing of Available Screenlets

Screenlets are grouped in modules based on internal dependencies. Each module is isolated, so you can use only the modules that are necessary for your project. However, it's important to note that you can't use a screenlet from a single module without using the entire module. The screenlets here are listed according to the module that they belong to.

- **Auth**: Module for user authentication. It uses the [user management](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/user-management) features of Liferay Portal. It includes the following screenlets:

	- [`LoginScreenlet`](Documentation/LoginScreenlet.md): Gives your app the ability to sign users in to a Liferay instance.
	- [`SignUpScreenlet`](Documentation/SignUpScreenlet.md): Gives your app the ability to sign new users in to a Liferay instance.
	- [`ForgotPasswordScreenlet`](Documentation/ForgotPasswordScreenlet.md): Gives your app the ability to send emails containing a new password or password reset link to users.
	- [`UserPortraitScreenlet`](Documentation/UserPortraitScreenlet.md): Gives your app the ability to show the user's portrait picture.

- **Dynamic Data Lists (DDL)**: Module for interacting with [Dynamic Data Lists](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/using-web-forms-and-dynamic-data-lists) in a Liferay instance. It includes the following screenlet:

	- [`DDLFormScreenlet`](Documentation/DDLFormScreenlet.md): Gives your app the ability to present dynamic forms to be filled by users and submitted back to the server.
	- [`DDLListScreenlet`](Documentation/DDLListScreenlet.md): Gives your app the ability to show a list of records based on a pre-existing DDL in a Liferay instance.

Also, some screenlets can be used individually without the need to import an entire module. These include:

- [`AssetListScreenlet`](Documentation/AssetListScreenlet.md): Shows a list of assets managed by [Liferay's Asset Framework](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en). This includes web content, blog entries, documents, and more.
- [`WebContentDisplayScreenlet`](Documentation/WebContentDisplayScreenlet.md): Shows the HTML of web content. This screenlet uses the features avaiable in [Web Content Management](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/web-content-management).

## Listing of Available Themes

With themes, you can control the look and feel of any screenlet that you decide to use in you app. What's more, these themes are fully pluggable. You can match the UI and UX of your app by installing new themes to extend and customize any screenlet.

The themes currently released with Liferay Screens are:

- **Default**: The standard theme that is used when you insert any screenlet on your screen. It can be used as the parent theme for any of your custom themes (refer to the [Architecture Guide](Documentation/architecture.md#theme-layer) for more details on this).
- **Flat7**: A sample theme intended to demonstrate how to develop your own full theme from scratch. For information on creating your own theme, refer to the [Theme Guide](Documentation/themes.md).
- **Westeros**: A sample theme intended to demonstrate how to develop your own full theme from scratch. For information on creating your own theme, refer to the [Theme Guide](Documentation/themes.md).

## Sample Apps

To learn how to configure and use the available screenlets, see the [Showcase app](Samples/README.md).

## Contributing New Screenlets and Themes

If you have a piece of code that can be reused in other apps, you may want to contribute it to the Liferay Screens project. Doing so is very straightforward: just follow the instructions in the [Contributors Guide](https://github.com/liferay/liferay-screens/tree/master/CONTRIBUTING.md).

