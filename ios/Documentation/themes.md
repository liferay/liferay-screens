# Themes in Liferay Screens for iOS

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains how _themes_ are designed and how you can create your own one. Liferay provides a limited set of themes, but themes can be created and contributed to the community.

## Installing themes

Right now, in order to install a theme in your app you just need to drag and drop the theme's folder into your project. Liferay Screens will detect the new classes and will apply the new look and feel, both in Interface Builder and runtime.

![Install Flat7 theme in XCode project](Images/xcode-install-theme.png)

As soon as CocoaPods is ready to work with Swift, it would be possible to publish a new recipe for your theme, so users would be able to install new themes just adding a new line in their `Podfile`.

## Using themes

To use themes, it's as simple as setting the `themeName` property in your Interface Builder.

![The themeName property in Interface Builder](Images/themes-property.png)

If you leave this property empty or type a wrong name, the Default theme will be used.


## Available themes

  - **Default**: The standard theme that is used when you insert any screenlet in your screen.
  - **Flat7**: A sample theme intended to demostrate how to develop your own theme from scratch.


## Creating Your Own Theme

Follow [this guide](theme_creation.md) in order to create your own theme.