# Creating a Theme in Liferay Screens for iOS

## Introduction

You can create your own theme to give your screenlets a different look, feel, and behavior. This document explains the steps required to create your own theme in Liferay Screens for iOS.

Before reading this guide, you may want to read the [Architecture Guide](architecture.md) in order to understand the underlying concepts. It may also be useful to read the [Screenlet Creation Guide](screenlet_creation.md).

The first step in creating a new theme is deciding what kind of theme to create:

- **Full theme**
- **Child theme**
- **Extended theme**

For more details on these theme types, refer the [themes section in the Architecture Guide](architecture.md#theme-layer).

You can create your theme's classes and resources directly inside your app project. If you want to use the theme in more than one app, follow the instructions below to [publish your theme as a library](#publish-your-themes-using-cocoapods).

The following steps illustrate theme creation by creating a new theme for the `LoginScreenlet`.

## Full Theme

A Full theme can present a completely different layout, using different components and input data. The example here presents just a single `UITextField` for the user name. The [UDID](http://www.idownloadblog.com/2010/12/21/iphone-udid/) is used for the password. 

1. Create a new `xib` called `LoginView_full.xib`. You'll build your new UI here as usual with Interface Builder. A good way to start is to duplicate `LoginView_default.xib` and use it as a template. Insert the text field for the user name and add a *Sign In* button with the same `restorationIdentifier` as the Default theme's button.

    ![New xib for full theme](Images/xcode-full-theme.png)

2. Create a new view class called `LoginView_full`. As before, you can duplicate the `LoginView_default` class and use it as a template. In this class, add all `@IBOutlet` properties or `@IBAction` methods you need to bind your UI components. This class must inherit the `BaseScreenletView` class and conform to the `LoginViewModel` protocol, implementing the corresponding getters and setters. In this case, the `password` getter returns the UDID.

3. Set `LoginView_full` as the custom class of your `LoginView_full.xib` file and bind your `@IBOutlet` and `@IBAction` to your class.

4. Install the new theme, insert the `LoginScreenlet` in any of your view controllers, and use the `full` value as the `themeName` screenlet's property. Your new look and feel is shown in Interface Builder.

## Child Theme

The example theme here presents the same components as the Default theme, but is adjusted to a larger resolution. This makes it appropriate for iOS devices such as the iPad, iPhone 6, and iPhone 6 Plus.

1. Create a new `xib` called `LoginView_large.xib`. You'll build your new UI here as usual with Interface Builder. A good place to start is to duplicate `LoginView_default.xib` and use it as a template for your new `xib`.

    ![New xib for child theme](Images/xcode-child-theme.png)

2. Change the position, size, or other properties of the parent components. However, be sure not to change the custom class, outlet connection, or `restorationIdentifier`.

3. Install your new theme, insert the `LoginScreenlet` in any of your view controllers, and use `large` as the value of the screenlet's `themeName` property. Your new look and feel is shown in Interface Builder.

## Extended Theme

The example theme here presents the same components as the Default theme, but sets new translated strings and contains a few new animations. A new UI component is also introduces to configure whether or not the password is shown when typed by the user.

For an example of this kind of theme, refer to the [Flat7 theme source code](https://github.com/liferay/liferay-screens/tree/master/ios/Framework/Themes/Flat7).

1. Create a new `xib` called `LoginView_ext.xib`. You'll build your new UI here as usual with Interface Builder. A good place to start is to duplicate `LoginView_default.xib` and use it as a template for your new `xib`. Add a new `UISwitch` component to configure the password presentation.

    ![New xib for extended theme](Images/xcode-ext-theme.png)

2. Create a new view class called `LoginView_ext` using `LoginView_default` as the parent class. To set new translations, you can override the `onSetTranslations` method. For other customizations, consider overriding other methods of the parent class, or add new delegates to the class. Add new `@IBOutlet` or `@IBAction` for the new `IBSwitch` component.

3. Set `LoginView_full` as the custom class of your `LoginView_ext.xib ` file and bind your new `@IBOutlet` and `@IBAction` to your class.

4. Install the new theme (drag & drop the classes and resources), insert `LoginScreenlet` in any of your view controllers, and use `ext` as the value for the screenlet's `themeName` property. Your new look and feel is shown in Interface Builder.

## Publish Your Themes Using CocoaPods

Since your theme is a code library, you can package it using CocoaPods. Doing so means that other developers are able to install and use your theme by simply adding the following line in their `Podfile`: 

    pod 'LiferayScreens-YourThemeName'
	
Use the following steps to package your theme for use with CocoaPods. *It's important that you use the same names and identifiers described in these steps*:

* Create an empty Xcode project choosing *Cocoa Touch Framework*.

    ![Choose *Cocoa Touch Framework* when creating a project for your theme.](Images/xcode-cocoa-touch-framework.png)
    
* Call your project `LiferayScreensThemeName` (change `Name` to your theme's name).

* Configure Liferay Screens for CocoaPods. To do this, follow the steps described in [Preparing Your Project for Liferay Screens](../README.md#preparing-your-project-for-liferay-screens).

* Create your theme's classes and resources according to the instructions in the above sections. Your classes must compile successfully in Xcode. Use caution if you create your own `xib` files. The custom class must use your theme's module name. For example, the Custom Class setting in this screenshot is incorrect. This is because the `xib` file is bound to the custom class name without specifying the module:

![The `xib` file is bound to the custom class name without specifying the module.](Images/xcode-theme-custom-module-wrong.png)

In the following screenshot, the setting for the custom class is correct:

![Xib file binded to custom class name specifying the module.](Images/xcode-theme-custom-module-right.png)

* In your project's root folder, add a file called `LiferayScreensTheme-Name.podspec` (change `Name` to your theme's name). It's very important the name of your project starts with `LiferayScreens`. Use the following content:

```ruby
	Pod::Spec.new do |s|
		s.name         = 'LiferayScreensThemeName'
		s.version      = '1.0'
		s.summary      = 'Your theme description'
		s.source = {
			:git => 'https://your_repository_url.git',
			:tag => 'v1.0'
		}
	
		s.platform = :ios, '8.0'
		s.requires_arc = true

		s.source_files = 'Your/Relative/Folder/**/*.{h,m,swift}'
		s.resources = 'Your/Relative/Folder/**/*.{xib,png,plist,lproj}'
	
		s.dependency 'LiferayScreens'
	end
```

Remember to change the occurences of `Name` and `name` to your theme's name in the following lines.

- `s.name = LiferayScreensThemeName`
- `LiferayScreens-name => Your/Folder/**/*`

* You can commit and push your project to your Git repository and then use the theme by adding the following line in your app's `Podfile`:

```ruby
	pod 'LiferayScreensThemeName', :git => 'https://your_repository_url.git'
```

* If you want to simplify the process even more, you can publish your theme as a public Pod. For instructions on this, see the chapter *Deploying a library* in the [official CocoaPods guide](https://guides.cocoapods.org/making/getting-setup-with-trunk.html#deploying-a-library).