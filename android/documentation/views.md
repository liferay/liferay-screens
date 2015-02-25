# Views in Liferay Screens for iOS

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains the basics of how views are used in Liferay Screens for Android. Liferay provides a limited set of views, but anyone can create easily additional new views.
Before start, we need to understand the different components in the view layer:

- *View* (sometimes refered as *Custom View* or *Screenlet View*): is the Java class that implements the behaviour of the UI. This class is usually the listener for the UI component's events.
- *Layout*: is the XML file that defines the UI components to be shown in the view. The root element is usually a view of the same type as the previous screenlet view.
- *View set*: the group of views for several screenlets together with their layouts is called *View Set*. A View Set usually have a name to easily refer to, like *Default* or *Material*. Anyone can create your own view set, giving a name and releasing it to the community.

## Installing View Sets

TODO Explain how to install theme-projects using gradle

## Using View Sets

It's as simple as setting the `liferay:layoutId` property in your layout's XML.

![The `liferay:layoutId` attribute used to change the layout](images/layoutid_xml.png)

## Available View Sets

- **Default**: standard views that will be used when you insert any screenlet on your screen and don't set the `liferay:layoutId` attribute.
- **Material**: sample views intended to demonstrate how to develop your own view sets from scratch.

## Creating Your Own Views

For instructions on creating your own views, please see the [Views Creation Guide](view_creation.md).