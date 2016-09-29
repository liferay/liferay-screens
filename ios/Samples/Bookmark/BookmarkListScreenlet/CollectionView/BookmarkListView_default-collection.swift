/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
import UIKit
import LiferayScreens

public class BookmarkListView_default_collection : BaseListCollectionView {

	let BookmarkCellId = "bookmarkCell"
	let HeaderId = "header"

	public override func doRegisterCellNibs() {
		let cellNib = UINib(nibName: "BookmarkCell_default-collection", bundle: nil)
		collectionView?.registerNib(cellNib,
				forCellWithReuseIdentifier: BookmarkCellId)

		let headerNib = UINib(nibName: "BookmarkHeaderView", bundle: nil)
		collectionView?.registerNib(headerNib,
				forSupplementaryViewOfKind: UICollectionElementKindSectionHeader,
				withReuseIdentifier: HeaderId)
	}

	public override func doFillLoadedCell(
			indexPath indexPath: NSIndexPath,
			cell: UICollectionViewCell,
			object: AnyObject) {

		guard let cell = cell as? BookmarkCell_default_collection, bookmark = object as? Bookmark
		else {
			return
		}
		
		cell.startLetter = String(hostFromString(bookmark.url).characters.first!)

		cell.url = bookmark.url
				.stringByReplacingOccurrencesOfString("http://", withString: "")
				.stringByReplacingOccurrencesOfString("https://", withString: "")
				.stringByReplacingOccurrencesOfString("www.", withString: "")

	}

	public override func doGetCellId(
			indexPath indexPath: NSIndexPath,
			object: AnyObject?) -> String {

		return BookmarkCellId
	}

	public override func doCreateLayout() -> UICollectionViewLayout {
		let layout = UICollectionViewFlowLayout()
		layout.itemSize = CGSize(width: 110, height: 110)
		layout.minimumLineSpacing = 10
		layout.minimumInteritemSpacing = 10

		return layout
	}

	public func collectionView(
			collectionView: UICollectionView,
			layout collectionViewLayout: UICollectionViewLayout,
			referenceSizeForHeaderInSection section: Int) -> CGSize {

		if section == 0 {
			return CGSize.zero
		}

		return CGSize(width: collectionView.bounds.width, height: 20)
	}

	public func collectionView(
			collectionView: UICollectionView,
			layout collectionViewLayout: UICollectionViewLayout,
			insetForSectionAtIndex section: Int) -> UIEdgeInsets {

		if section == sections.startIndex || section == sections.endIndex {
			return UIEdgeInsets(top: 0, left: 10, bottom: 0, right: 10)
		}

		return UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)
	}


	public func collectionView(
			collectionView: UICollectionView,
			viewForSupplementaryElementOfKind kind: String,
			atIndexPath indexPath: NSIndexPath) -> UICollectionReusableView {

		let headerView = collectionView.dequeueReusableSupplementaryViewOfKind(
				UICollectionElementKindSectionHeader,
				withReuseIdentifier: HeaderId, forIndexPath: indexPath)

		if let headerView = headerView as? BookmarkHeaderView {
			headerView.sectionTitle = sections[indexPath.section]
		}

		return headerView
	}

	public func hostFromString(urlString: String) -> String {
		let host = NSURL(string: urlString)?.host ?? ""

		return host.stringByReplacingOccurrencesOfString("www.", withString: "")
	}
}