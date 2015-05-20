# AssetListScreenlet for iOS

## Important Note

_This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology._

## Requirements

- XCode 6.0 or above
- iOS 8 SDK
- Liferay Portal 6.2 CE or EE
- Liferay Screens' Compatibility Plugin installed

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

Dynamic Data Lists and Data Types should be configured properly in the portal. Refer to the [Defining Data Types](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/building-a-list-platform-in-liferay-and-defining-data-) and [Creating Data Lists](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/creating-data-lists) sections of the User Guide for more details.

## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
| `autoLoad` | `boolean` | Whether the list should be loaded when it's presented on the screen. The default value is `true`. |
| `refreshControl` | `boolean` | Whether a standard [UIRefreshControl](https://developer.apple.com/library/ios/documentation/UIKit/Reference/UIRefreshControl_class/) is shown when the user does the pull to refresh gesture. The default value is `true`. |
| `firstPageSize` | `number` | The number of items retrieved from the server for display on the first page. The default value is `50`. |
| `pageSize` | `number` | The number of items retrieved from the server for display on the second and subsequent pages. The default value is `25`. |
| `groupId` | `number` | The ID of the site (group) where the asset is stored. If set to `0`, the `groupId` specified in `LiferayServerContext` is used. The default value is `0`. |
| `classNameId` | `number` | The ID of the asset's class name. Use values from the `AssetClassNameId` enumeration or the `classname_` database table. |

## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
| `loadList()` | `boolean` | Starts the request to load the list of assets. This list is shown when the response is received. Returns `true` if the request is sent. |

## Delegate

The `AssetListScreenlet` delegates some events to an object that conforms to the `AssetListScreenletDelegate` protocol. This protocol lets you implement the following methods:

- `- screenlet:onAssetListResponseEntries:`: Called when a page of assets is received. Note that this method may be called more than once; one call for each page received.
- `- screenlet:onAssetListError:`: Called when an error occurs in the process. The `NSError` object describes the error.
- `- screenlet:onAssetSelectedEntry:`: Called when an item in the list is selected.
