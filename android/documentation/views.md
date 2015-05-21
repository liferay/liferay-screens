# Views in Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains the basics of how views are used in Liferay Screens for Android. Liferay provides a limited set of views, but new views can easily be created. Before starting, you should understand the different components in the view layer:

- *View*: Sometimes referred to as the *Custom View* or *Screenlet View*. This is the Java class that implements the UI's behavior. This class is usually the listener for the UI component's events.
- *Layout*: The XML file that defines the UI components in the view. The root element is usually the same view type as the previous screenlet view.
- *View set*: The group of views for several screenlets together with their layouts. A view set usually has a name that is easy to refer to, such as *Default*, *Material* or *Westeros*. Anyone can create their own view set and release it to the community.

## Installing View Sets

When you set up your project it includes only the Default view set. Two additional view sets, Material and Westeros, are available as dependencies in jCenter. To add them, include the following code in your `build.gradle` file. Note that you don't have to use both; you can omit the `compile` statement for the view set you don't want to include. 

```groovy
dependencies {
    ...
    compile 'com.liferay.mobile:liferay-material-viewset:1.0.+'
    compile 'com.liferay.mobile:liferay-westeros-viewset:1.0.+'	
    ...
}
```

Anyone can create a new view set and publish it in a public repository like Maven Central or jCenter. To use such a view set, include the artifact containing the view set by using the standard steps for Gradle or Maven. These steps are described in the section [Preparing Your Project for Liferay  Screens](https://github.com/liferay/liferay-screens/tree/master/android/README.md#preparing-your-project-for-liferay-screens).

## Using View Sets

To use a view set, set the `liferay:layoutId` property in your layout XML. This is shown in the following screenshot:

![The `liferay:layoutId` attribute is used to change the layout.](images/layoutid_xml.png)

## Available View Sets

- **Default**: Standard views used when you insert any screenlet and don't set the `liferay:layoutId` attribute.
- **Material**: Sample views intended to demonstrate how to develop your own view sets from scratch. It follows the [Material Design](https://developer.android.com/design/material/index.html) guidelines published by Google.
- **Westeros**: A custom view set created to customize the behaviour and appearance of a demo application called "Westeros Bank".

## Creating Your Own Views

For instructions on creating your own views, see the [View Creation Guide](view_creation.md).