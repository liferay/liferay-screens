# PortraitScreenlet for iOS

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- XCode 6.0 or above
- iOS 8 SDK
- Liferay Portal 6.2 CE or EE

## Compatibility

- iOS 7 and above

## Features

The `PortraitScreenlet` shows the user portrait configured in the Liferay Portal. If the user doesn't have a protrait configured, it shows a placeholder image.

## Module

- None

## Themes

- Default
- Flat7

![The `PortraitScreenlet` using both the Default and Flat7 themes](Images/portrait.png)

## Portal Configuration

None

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
| `borderWidth` | `number` | The size (in pixels) for the border of the portrait. Default value is 1 pixel. If you want to hide the border at all, use 0 pixels here.|
|  `borderColor` | `UIColor` | The color to be used in the border. If you want to hide the border at all, use clear color here. |

## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
|  `loadLoggedUserPortrait()` | `boolean` | Starts the request to load the portrait image for the user currently logged in (see `SessionContext` class) |
|  `load(userId)` | `boolean` | Starts the request to load the portrait image for specified user |
|  `load(portraitId, uuid, male)` | `boolean` | Starts the request to load the portrait image for specified user's data. `PortraitId` and `uuid` can be got using `SessionContext.userAttribute()` method.|

## Delegate

The `PortraitScreenlet` delegates some events to an object that conforms to the `PortraitScreenletDelegate ` protocol. This protocol lets you implement the following methods:

- `onPortraitResponse(image)`: Called when an image is received from the server. You can here apply some filters to the image (i.e. grayscale) and return the new image or just return the image supplied as the argument if you don't want to modify it.
- `onPortraitError(error)`: Called when an error occurs in the process. The `NSError` object describes the error.

