# Creating a Theme in Liferay Screens for iOS

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

You can create your own theme to give your screenlets a different look, feel, and behavior. This document explains the steps required to create your own theme in Liferay Screens for iOS.

Before reading this guide, you may want to read the [Architecture Guide](architecture.md) in order to understand the underlying concepts. It may also be useful to read the [Screenlet Creation Guide](screenlet_creation.md).

The first step in creating a new theme is deciding what kind of theme to create:

- **Full theme**
- **Child theme**
- **Extended theme**

For more details on these theme types, refer the [themes section in the Architecture Guide](architecture.md#theme-layer).

The following steps illustrate theme creation by creating a new theme for the `LoginScreenlet`.

## Full Theme

A Full theme can present a completely different layout, using different components and input data. The example here presents just a single `UITextField` for the user name. The [UDID](http://www.idownloadblog.com/2010/12/21/iphone-udid/) is used for the password. 

1. Create a new `xib` called `LoginScreenlet_full.xib`. You'll build your new UI here as usual with Interface Builder. A good way to start is to duplicate `LoginScreenlet_default.xib` and use it as a template. Insert the text field for the user name and add a *Sign In* button with the same `restorationIdentifier` as the Default theme's button.

    ![New xib for full theme](Images/xcode-full-theme.png)

2. Create a new view class called `LoginScreenletView_full`. As before, you can duplicate the `LoginScreenletView_default` class and use it as a template. In this class, add all `@IBOutlet` properties or `@IBAction` methods you need to bind your UI components. This class must inherit the `BaseScreenletView` class and conform to the `LoginScreenletData` protocol, implementing the corresponding getters and setters. In this case, the `password` getter returns the UDID.

3. Set `LoginScreenletView_full` as the custom class of your `LoginScreenlet_full.xib` file and bind your `@IBOutlet` and `@IBAction` to your class.

4. Install the new theme, insert the `LoginScreenlet` in any of your view controllers, and use the `full` value as the `themeName` screenlet's property. Your new look and feel is shown in Interface Builder.

## Child Theme

The example theme here presents the same components as the Default theme, but is adjusted to a larger resolution. This makes it appropriate for iOS devices such as the iPad, iPhone 6, and iPhone 6 Plus.

1. Create a new `xib` called `LoginScreenlet_large.xib`. You'll build your new UI here as usual with Interface Builder. A good place to start is to duplicate `LoginScreenlet_default.xib` and use it as a template for your new `xib`.

    ![New xib for child theme](Images/xcode-child-theme.png)

2. Change the position, size, or other properties of the parent components. However, be sure not to change the custom class, outlet connection, or `restorationIdentifier`.

3. Install your new theme, insert the `LoginScreenlet` in any of your view controllers, and use `large` as the value of the screenlet's `themeName` property. Your new look and feel is shown in Interface Builder.

## Extended Theme

The example theme here presents the same components as the Default theme, but sets new translated strings and contains a few new animations. A new UI component is also introduces to configure whether or not the password is shown when typed by the user.

For an example of this kind of theme, refer to the [Flat7 theme source code](https://github.com/liferay/liferay-screens/tree/master/ios/Library/Themes/Flat7).

1. Create a new `xib` called `LoginScreenlet_ext.xib`. You'll build your new UI here as usual with Interface Builder. A good place to start is to duplicate `LoginScreenlet_default.xib` and use it as a template for your new `xib`. Add a new `UISwitch` component to configure the password presentation.

    ![New xib for extended theme](Images/xcode-ext-theme.png)

2. Create a new view class called `LoginScreenletView_ext` using `LoginScreenletView_default` as the parent class. To set new translations, you can override the `onSetTranslations` method. For other customizations, consider overriding other methods of the parent class, or add new delegates to the class. Add new `@IBOutlet` or `@IBAction` for the new `IBSwitch` component.

3. Set `LoginScreenletView_full` as the custom class of your `LoginScreenlet_ext.xib ` file and bind your new `@IBOutlet` and `@IBAction` to your class.

4. Install the new theme (drag & drop the classes and resources), insert `LoginScreenlet` in any of your view controllers, and use `ext` as the value for the screenlet's `themeName` property. Your new look and feel is shown in Interface Builder.

## Publish your themes using CocoaPods 

You can package your themes using CocoaPods, just as it's a library of code (in fact, it is!).
If you do this, other developers will be able to install and use your theme just adding the following line in their `Podfile`:

	pod 'LiferayScreens-YourThemeName'
	
To allow this, just follow the next steps (__it is important you use the same names and identifiers described in the following steps__):

* Create an empty project choosing "Cocoa Touch Framework".

    ![Choose "Cocoa Touch Framework"](Images/xcode-cocoa-touch-framework.png)
    
* Call your project `LiferayScreensThemeName` (changing `Name` by your theme's name)

* Configure LiferayScreens using CocoaPods following the steps described in [Prepare Your Project for Liferay Screens](../README.md#preparing-your-project-for-liferay-screens).

* Create your theme classes and resources following the above instructions. Your classes must compile successfully in Xcode. Be carefull if you create Xib files. The custom class must use your theme's module name:

__Wrong__
    ![Xib file binded to custom class name without specifying the module](Images/xcode-theme-custom-module-wrong.png)

__Right__
    ![Xib file binded to custom class name specifying the module](Images/xcode-theme-custom-module-right.png)

* In your project's root folder, add a file called `LiferayScreensTheme-Name.podspec` (changing `Name` by your theme's name) with the following content:

```ruby
	Pod::Spec.new do |s|
		s.name         = 'LiferayScreensTheme-Name'
		s.module_name  = 'LiferayScreensThemeName'
		s.version      = '1.0'
		s.summary      = 'Your theme description'
		s.source = {
			:git => 'https://your_repository_url.git',
			:tag => 'v1.0'
		}
	
		s.platform = :ios
		s.ios.deployment_target = '8.0'
		s.requires_arc = true

		s.source_files = 'Your/Relative/Folder/**/*.{h,m,swift}'

		s.resource_bundle = {
			'LiferayScreens-name' => 'Your/Relative/Folder/**/*.{xib,png,plist,lproj}'
		}
	
		s.dependency 'LiferayScreens'
	end
```

_Remember to change the occurences of `Name` to your theme's name in the following lines_

- _s.name         = 'LiferayScreensTheme-___Name___'_
- _s.module\_name  = 'LiferayScreensTheme___Name___'_
- _'LiferayScreens-___name___' => 'Your/Folder/**/*._

* You can commit and push your project to your Git repository and use the theme just adding the following line in app's `Podfile`:

```ruby
	pod 'LiferayScreens-YourThemeName', :git => 'https://your_repository_url.git'
```

* If you want to simplify the process even more, you can publish your theme as a public Pod. For that, follow the chapter "Deploying a library" in the [official guide](https://guides.cocoapods.org/making/getting-setup-with-trunk.html#deploying-a-library).

