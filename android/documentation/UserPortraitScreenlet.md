# UserPortraitScreenlet for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- Android SDK 4.0 (API Level 14) and above
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed
- Picassa library

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The `UserPortraitScreenlet` shows the user's portrait from Liferay Portal. If the user doesn't have a portrait configured, a placeholder image is shown.

## Module

- None

## Views

- Default
- Material

PICTURE ![The `PortraitScreenlet` using the Default and Flat7 themes.](Images/portrait.png)

## Portal Configuration

None

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `layoutId` | `@layout` | The layout to be used to show the view.|
|  `autoLoad` | `boolean` | Whether or not the portrait image should be loaded when the screenlet is attached to the window. |
| `userId` | `number` | The `userId` of the user's portrait. If this attribute is set, `male`, `portraitId` and `uuid` attributes will be ignored.|
|  `male` | `boolean` | Whether the default portrait placeholder will show a male or female outline. This attribute is used if `userId` is not specified. |
|  `portraitId` | `number` | The `portraitId` to load. This attribute is used if `userId` is not specified.|
|  `uuid` | `string` | The `uuid` of the user to load. This attribute is used if `userId` is not specified. |


## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
|  `load()` | `void` | Starts the request to load the user specified in the `userId` property or the portrait specified in the `portraitId`and `uuid` properties. |

## Listener

The `UserPortraitScreenlet` delegates some events to an object that implements the `UserPortraitListener` interface. This interface let you implement the following methods:

| Method | Explanation |
|-----------|-------------| 
|  <pre>onUserPortraitReceived(<br/>        UserPortraitScreenlet source, <br/>        Bitmap bitmap)</pre> | Called when an image is received from the server. You can then apply image filters (grayscale, for example) and return the new image. You can return `null` or the original image supplied as the argument if you don't want to modify it.|
|  <pre>onUserPortraitFailure(<br/>        UserPortraitScreenlet source, <br/>        Exception e)</pre> | Called when an error occurs in the process.|

