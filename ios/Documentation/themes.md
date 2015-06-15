# Themes in Liferay Screens for iOS

## Introduction

This document explains the basics of how themes are designed in Liferay Screens for iOS. Liferay provides a limited set of themes, but additional ones can be created and contributed to the community.

## Installing Themes

Right now, to install a theme in your app you have two options, depending on how the theme has been published:

* If the theme has been packaged as a pod dependency you can install and use your theme by simply adding the following line in your Podfile:

```ruby
	pod 'LiferayScreens-YourThemeName'
```

* Or you can drag and drop the theme's folder into your project. Liferay Screens detects the new classes and then applies the new look and feel, in both the Interface Builder and runtime.

![Installing the Flat7 theme in an XCode project.](Images/xcode-install-theme.png)

## Using Themes

To use themes, it's as simple as setting the `themeName` property in your Interface Builder. If you leave this property empty or type a wrong name, the Default theme will be used.

![The `themeName` property in Interface Builder.](Images/themes-property.png)

## Available Themes

- **Default**: The standard theme that is used when you insert any screenlet on your screen.
- **Flat7**: A sample theme intended to demonstrate how to develop your own theme from scratch.
- **Westeros**: A theme used in the demo Westeros Bank App.

## Creating Your Own Theme

For instructions on creating your own theme, please see the [Theme Creation Guide](theme_creation.md).