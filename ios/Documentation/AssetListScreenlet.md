# AssetListScreenlet for iOS

## Important Note

_This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology._

## Requirements

- XCode 6.0 or above
- iOS 8 SDK
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatibility

- iOS 7 and above

## Features

The `AssetListScreenlet` can be used to show lists of [assets](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en) from a Liferay instance. For example, you can use the screenlet to show a scrollable collection of assets. It also implements [fluent pagination](http://www.iosnomad.com/blog/2014/4/21/fluent-pagination) with configurable page size. The `AssetListScreenlet` can show assets of the following classes:

- `Group`
- `Layout`
- `Organization`
- `User`
- `UserGroup`
- `BlogsEntry`
- `BookmarksEntry`
- `BookmarksFolder`
- `CalendarEvent`
- `DLFileEntry`
- `DLFileEntryMetadata`
- `DLFileEntryType`
- `DLFileRank`
- `DLFileShortcut`
- `DLFileVersion`
- `DDLRecord`
- `DDLRecordSet`
- `JournalArticle` (Web Content)
- `JournalFolder`
- `MBMessage`
- `MBThread`
- `MBCategory`
- `MBDiscussion`
- `MBMailingList`
- `WikiPage`
- `WikiPageResource`
- `WikiNode`

The `AssetListScreenlet` also supports i18n in asset values.

## Module

- None

## Themes

The Default theme uses a standard `UITableView` to show the scrollable list. Other themes may use a different component, such as `UICollectionView` or others, to show the items.

![`AssetListScreenlet` using the Default theme.](Images/assetlist.png)

## Portal Configuration

Dynamic Data Lists and Data Types should be configured properly in the portal. Refer to [Defining Data Types](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/building-a-list-platform-in-liferay-and-liferay-portal-6-2-user-guide-10-en) and [Creating Data Lists](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/creating-data-lists-liferay-portal-6-2-user-guide-10-en) sections for more details.

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `groupId` | `number` | The site (group) identifier where the asset will be stored. If this value is 0, the `groupId` specified in `LiferayServerContext` is be used. |
|  `classNameId` | `number` | The identifier of asset's class name. Use values from `AssetClassNameId` enumeration or the `classname_` database table. |

## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
|  `loadList()` | `boolean` | Starts the request to load the list of assets. This list is shown when the response is received. Returns `true` if the request could be sent. |

## Delegate

The `AssetListScreenlet` delegates some events to an object that conforms to the `AssetListScreenletDelegate` protocol. This protocol lets you implement the following methods:

- `onAssetListResponse(list of records)`: Called when a page of assets is received. Note that this method may be called more than once; one call for each page received.
- `onAssetListError(error)`: Called when an error occurs in the process. The `NSError` object describes the error.
- `onAssetListSelected(asset)`: Called when an item in the list is selected.

