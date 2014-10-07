# AssetListScreenlet for iOS

## Important Note

_This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology._

## Features
- Shows a collection of [assets](https://www.liferay.com/documentation/liferay-portal/6.2/development/-/ai/asset-framework-liferay-portal-6-2-dev-guide-06-en) in a scrolleable way
- Implements [fluent pagination](http://www.iosnomad.com/blog/2014/4/21/fluent-pagination) with configurable page size.
- Allow to show asset of one the following class names:
	- Group
	- Layout
	- Organization
	- User
	- UserGroup
	- BlogsEntry
	- BookmarksEntry
	- BookmarksFolder
	- CalendarEvent
	- DLFileEntry
	- DLFileEntryMetadata
	- DLFileEntryType
	- DLFileRank
	- DLFileShortcut
	- DLFileVersion
	- DDLRecord
	- DDLRecordSet
	- JournalArticle (Web Content)
	- JournalFolder
	- MBMessage
	- MBThread
	- MBCategory
	- MBDiscussion
	- MBMailingList
	- WikiPage
	- WikiPageResource
	- WikiNode

- Supports i18n in asset values.

##Module
None

## Themes
- Default: uses a standard `UITableView` to show the scrolleable list. Other themes may use a different component (`UICollectionView` or whatever) to show the items.

![AssetList screenlet using Default theme](Images/assetlist.png)

## Requirements

- XCode 6.0 or above
- iOS 8 SDK
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatiblity

- iOS 7 and above

## Portal configuration

Dynamic Data List and Data Types should be configured properly in the portal.

Refer to [Defining Data Types](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/building-a-list-platform-in-liferay-and-liferay-portal-6-2-user-guide-10-en) and [Creating Data Lists](https://www.liferay.com/documentation/liferay-portal/6.2/user-guide/-/ai/creating-data-lists-liferay-portal-6-2-user-guide-10-en) sections for more details.


## Attributes

| Attribute | Data type | Explanation |
|-----------|-----------|-------------| 
|  `groupId` | `number` | The site (group) identifier where the asset will be stored. If this value is 0, the `groupId` specified in `LiferayServerContext` will be used|
|  `classNameId` | `number` | The identifier of asset's class name. Use values from `AssetClassNameId` enumeration or from the `classname_` database table. |


## Methods

| Method | Return | Explanation |
|-----------|-----------|-------------| 
|  `loadList()` | `boolean` | Starts the request to load the list of assets. When the response is received, the list is shown. Returns `true` if the request could be sent. |


## Delegate

This screenlet delegates some events in an object that conforms `AssetListScreenletDelegate` protocol.
This protocol allows to implement the following methods:

- `onAssetListResponse(list of records)`: called when a page of assets is received. Note this method may be called more than once, one for each page retrieved.
- `onAssetListError(error)`: called when an error happened in the process. The NSError object describes the error occurred.
- `onAssetListSelected(asset)`: called when an item in the list is selected.



    
    
