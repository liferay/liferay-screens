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

The `PortraitScreenlet` shows the user's portrait from Liferay Portal. If the user doesn't have a portrait configured, a placeholder image is shown.

## Module

- None

## Themes

- Default
- Flat7

![The `PortraitScreenlet` using the Default and Flat7 themes.](Images/portrait.png)

## Portal Configuration

None

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
| `borderWidth` | `number` | The size in pixels for the portrait's border. The default value is 1. Set this to 0 if you want to hide the border.|
|  `borderColor` | `UIColor` | The border's color. Use clear color to hide the border. |

## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
|  `loadLoggedUserPortrait()` | `boolean` | Starts the request to load the currently logged in user's portrait image (see the `SessionContext` class). |
|  `load(userId)` | `boolean` | Starts the request to load the specified user's  portrait image. |
|  `load(portraitId, uuid, male)` | `boolean` | Starts the request to load the portrait image using the specified user's data. The parameters `portraitId` and `uuid` can be retrieved by using the `SessionContext.userAttribute()` method.|

## Delegate

The `PortraitScreenlet` delegates some events to an object that conforms to the `PortraitScreenletDelegate ` protocol. This protocol lets you implement the following methods:

- `onPortraitResponse(image)`: Called when an image is received from the server. You can then apply image filters (grayscale, for example) and return the new image. You can return the original image supplied as the argument if you don't want to modify it.
- `onPortraitError(error)`: Called when an error occurs in the process. The `NSError` object describes the error.

