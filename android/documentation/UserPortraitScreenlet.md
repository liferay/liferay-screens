# UserPortraitScreenlet for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- Android SDK 4.0 (API Level 14) and above
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin
- Picasa library

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The `UserPortraitScreenlet` shows the user's profile picture from their Liferay Portal. If the user doesn't have a profile picture, a placeholder image is shown.

## Module

- None

## Views

- Default
- Material

![The `UserPortraitScreenlet` using the Default and Material viewsets](images/userportrait.png)

## Portal Configuration

No additional steps required.

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
| `layoutId` | `@layout` | The layout used to show the view. |
| `autoLoad` | `boolean` | Whether the portrait should load when the screenlet is attached to the window. |
| `userId` | `number` | The ID of the user whose portrait is being requested. If this attribute is set, the `male`, `portraitId`, and `uuid` attributes are ignored. |
| `male` | `boolean` | Whether the default portrait placeholder shows a male or female outline. This attribute is used if `userId` isn't specified. |
| `portraitId` | `number` | The ID of the portrait to load. This attribute is used if `userId` isn't specified. |
| `uuid` | `string` | The `uuid` of the user whose portrait is being requested. This attribute is used if `userId` isn't specified. |

## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
| `load()` | `void` | Starts the request to load the user specified in the `userId` property, or the portrait specified in the `portraitId`and `uuid` properties. |

## Listener

The `UserPortraitScreenlet` delegates some events to an object that implements the `UserPortraitListener` interface. This interface lets you implement the following methods:

| Method | Explanation |
|-----------|-------------| 
|  <pre>onUserPortraitReceived(<br/>        UserPortraitScreenlet source, <br/>        Bitmap bitmap)</pre> | Called when an image is received from the server. You can then apply image filters (grayscale, for example) and return the new image. You can return `null` or the original image supplied as the argument if you don't want to modify it. |
|  <pre>onUserPortraitFailure(<br/>        UserPortraitScreenlet source, <br/>        Exception e)</pre> | Called when an error occurs in the process. |
