# Views in Liferay Screens for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology*.

## Introduction

This document explains the basics of how views are used in Liferay Screens for Android. Liferay provides a limited set of views, but new views can easily be created. Before starting, you should understand the different components in the view layer:

- *View*: Sometimes refered to as the *Custom View* or *Screenlet View*. This is the Java class that implements the UI's behavior. This class is usually the listener for the UI component's events.
- *Layout*: The XML file that defines the UI components in the view. The root element is usually the same view type as the previous screenlet view.
- *View set*: The group of views for several screenlets together with their layouts. A view set usually has a name that is easy to refer to, such as *Default* or *Material*. Anyone can create their own view set and releasing it to the community.

<!--
## Installing View Sets

TODO Explain how to install theme-projects using gradle
-->

## Using View Sets

To use a view set, set the `liferay:layoutId` property in your layout XML. This is shown in the following screenshot:

![The `liferay:layoutId` attribute is used to change the layout.](images/layoutid_xml.png)

## Available View Sets

- **Default**: Standard views used when you insert any screenlet and don't set the `liferay:layoutId` attribute.
- **Material**: Sample views intended to demonstrate how to develop your own view sets from scratch.

## Creating Your Own Views

For instructions on creating your own views, see the [View Creation Guide](view_creation.md).