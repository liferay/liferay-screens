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


public class BaseListCollectionView : BaseListView, UICollectionViewDataSource, UICollectionViewDelegate {

	let defaultCellId = "liferay-screns-loadingMoreCellId"

	@IBOutlet public var collectionView: UICollectionView?

	internal var refreshControlView: UIRefreshControl?

	internal var refreshClosure: (Void -> Bool)? {
		didSet {
			updateRefreshControl()
		}
	}
	
	override public var progressMessages: [String:ProgressMessages] {
		return [
			BaseListScreenlet.LoadInitialPageAction : [
				.Working : LocalizedString("core", key: "base-list-loading-message", obj: self),
				.Failure : LocalizedString("core", key: "base-list-loading-error", obj: self)
			]
		]
	}

	// MARK: BaseListView
	
	public override func onCreated() {
		super.onCreated()
		
		collectionView?.delegate = self
		collectionView?.dataSource = self
		
		doConfigureCollectionView(collectionView!)
		doRegisterCellNibs()
		doRegisterLoadMoreCell()
	}

	public override func onShow() {
		super.onShow()

		// Force layout
		layoutIfNeeded()
		collectionView?.collectionViewLayout = doCreateLayout()
	}
	
	override public func onChangedRows(oldRows: [String : [AnyObject?]]) {
		super.onChangedRows(oldRows)

		if oldRows[BaseListView.DefaultSection]!.isEmpty {
			insertFreshRows()
			return
		}

		let moreRowsThanExpected = (rows[BaseListView.DefaultSection]!.count >
			oldRows[BaseListView.DefaultSection]!.count)

		let lessRowsThanExpected = (rows[BaseListView.DefaultSection]!.count <
			oldRows[BaseListView.DefaultSection]!.count)

		if  moreRowsThanExpected {
			turnStreamModeOn()
			onAddedRows(oldRows)
		}
		else if lessRowsThanExpected {
			//Only executed in fluent mode so there is only one section
			deleteRows(from: rows[BaseListView.DefaultSection]!.count,
			           to: oldRows[BaseListView.DefaultSection]!.count, section: 0)
		}

		let visibleRows = collectionView!.indexPathsForVisibleItems()
		updateVisibleRows(visibleRows)
	}

	override public func onAddedRows(oldRows: [String : [AnyObject?]]) {
		collectionView?.reloadData()
	}

	public override func onClearRows(oldRows: [String : [AnyObject?]]) {
		clearAllRows(oldRows)
	}

	override public func onFinishInteraction(result: AnyObject?, error: NSError?) {
		if let currentRefreshControl = refreshControlView {
			dispatch_delayed(0.3) {
				currentRefreshControl.endRefreshing()
			}
		}
	}

	//MARK: UITableViewDataSource

	public func collectionView(
			collectionView: UICollectionView,
			numberOfItemsInSection section: Int)
			-> Int {

		let streamMode = (screenlet as! BaseListScreenlet).streamMode

		if streamMode && moreRows {
			return rowsForSectionIndex(section).count + 1
		}

		return rowsForSectionIndex(section).count
	}

	public func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
		return sections.count
	}

	public func collectionView(collectionView: UICollectionView,
			cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {

		let rowsForSection = rowsForSectionIndex(indexPath.section)

		let isProgressViewCell = indexPath.row == rowsForSectionIndex(indexPath.section).count

		if isProgressViewCell {
			let cell = collectionView.dequeueReusableCellWithReuseIdentifier(
				doGetLoadMoreCellId(),
				forIndexPath: indexPath)

			doFillLoadMoreCell(cell)

			return cell
		}

		let object: AnyObject? = rowsForSection[indexPath.row]
		let cell = doDequeueReusableCell(indexPath: indexPath, object: object)

		if let object = object {
			doFillLoadedCell(indexPath: indexPath, cell: cell, object: object)
		}
		else {
			doFillInProgressCell(indexPath: indexPath, cell: cell)

			let streamMode = (screenlet as! BaseListScreenlet).streamMode

			if !streamMode {
				fetchPageForRow?(indexPath.row)
			}
		}

		return cell
	}

	public func collectionView(
			collectionView: UICollectionView,
			didSelectItemAtIndexPath indexPath: NSIndexPath) {
			
		collectionView.deselectItemAtIndexPath(indexPath, animated: false)

		let rowsForSection = rowsForSectionIndex(indexPath.section)

		if let row:AnyObject = rowsForSection[indexPath.row] {
			onSelectedRowClosure?(row)
		}

	}

	public func collectionView(
			collectionView: UICollectionView,
			willDisplayCell cell: UICollectionViewCell,
			forItemAtIndexPath indexPath: NSIndexPath) {

		let streamMode = (screenlet as! BaseListScreenlet).streamMode

		if streamMode && !loadingRows && moreRows {

			let isLastSection = (indexPath.section == sections.count - 1)
			let isLastRowForSection = (indexPath.row == rowsForSectionIndex(indexPath.section).count - 1)

			if isLastSection && isLastRowForSection {
				loadingRows = true
				let lastRow = rows.values.reduce(0) {$0 + $1.count}
				fetchPageForRow?(lastRow + 1)
			}
		}
	}

	public func collectionView(
			collectionView: UICollectionView,
			layout collectionViewLayout: UICollectionViewLayout,
			sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {

		// This method is only called when layout is a instance of UICollectionViewFlowLayout
		let layout = collectionViewLayout as! UICollectionViewFlowLayout

		if indexPath.row == rowsForSectionIndex(indexPath.section).count {
			return doGetLoadMoreViewSize(layout)
		}

		return layout.itemSize
	}

	public func doConfigureCollectionView(collectionView: UICollectionView) {
		collectionView.backgroundColor = .whiteColor()
	}

	public func doCreateLayout() -> UICollectionViewLayout {
		let layout = UICollectionViewFlowLayout()
		layout.itemSize = CGSize(width: 100, height: 100)
		layout.sectionInset = UIEdgeInsetsZero
		return layout
	}

	public func doDequeueReusableCell(
			indexPath indexPath: NSIndexPath,
			object: AnyObject?) -> UICollectionViewCell {

		let cellId = doGetCellId(indexPath: indexPath, object: object)

		return collectionView!.dequeueReusableCellWithReuseIdentifier(cellId, forIndexPath: indexPath)
	}

	public func doFillLoadedCell(
			indexPath indexPath: NSIndexPath,
			cell: UICollectionViewCell, object:AnyObject) {

	}

	public func doFillInProgressCell(indexPath indexPath: NSIndexPath, cell: UICollectionViewCell) {
	}

	public func doRegisterCellNibs() {
	}

	public func doRegisterLoadMoreCell() {
		collectionView?.registerClass(
				UICollectionViewCell.self,
				forCellWithReuseIdentifier: defaultCellId)
	}

	public func doGetCellId(indexPath indexPath: NSIndexPath, object: AnyObject?) -> String {
		return defaultCellId//"defaultCellId"
	}

	public func doGetLoadMoreCellId() -> String {
		return defaultCellId
	}

	public func doCreateCell(cellId: String) -> UICollectionViewCell {
		return UICollectionViewCell()
	}

	public func doFillLoadMoreCell(cell: UICollectionViewCell) {
		if cell.contentView.viewWithTag(99) == nil {
			let loadingMoreView = UIActivityIndicatorView(activityIndicatorStyle: .Gray)
			loadingMoreView.center = CGPoint(
				x: cell.contentView.bounds.width/2,
				y: cell.contentView.bounds.height/2)
			loadingMoreView.tag = 99

			loadingMoreView.startAnimating()

			cell.contentView.addSubview(loadingMoreView)
		}
	}

	public func doGetLoadMoreViewSize(layout: UICollectionViewFlowLayout) -> CGSize {
		if layout.scrollDirection == .Vertical {
			return CGSize(width: layout.itemSize.width, height: 30)
		}

		return CGSize(width: 30, height: layout.itemSize.height)
	}

	public func updateRefreshControl() {
		if refreshClosure != nil {
			if refreshControlView == nil {
				refreshControlView = UIRefreshControl()
				collectionView?.addSubview(refreshControlView!)
				collectionView?.alwaysBounceVertical = true
				refreshControlView!.addTarget(
						self,
						action: #selector(BaseListTableView.refreshControlBeginRefresh(_:)),
						forControlEvents: .ValueChanged)
			}
		}
		else if let currentControl = refreshControlView {
			currentControl.endRefreshing()
			currentControl.removeFromSuperview()
			refreshControlView = nil
		}
	}


	//MARK: Internal methods

	internal func refreshControlBeginRefresh(sender:AnyObject?) {
		dispatch_delayed(0.3) {
			if !self.loadingRows {
				self.moreRows = true
				self.refreshClosure?()
			}
			else {
				self.refreshControlView?.endRefreshing()
			}
		}
	}

	internal func insertFreshRows() {
		collectionView?.reloadData()
	}

	internal func insertRows(from from: Int, to: Int, section: Int) {
		let indexPaths = (from..<to).map {
			NSIndexPath(forRow: $0, inSection: section)
		}
		collectionView!.insertItemsAtIndexPaths(indexPaths)
	}

	internal func clearAllRows(currentRows: [String : [AnyObject?]]) {
		collectionView?.reloadData()
	}

	internal func deleteRows(from from: Int, to: Int, section: Int) {
		let indexPaths = (from..<to).map {
			NSIndexPath(forRow: $0, inSection: section)
		}
		collectionView?.deleteItemsAtIndexPaths(indexPaths)
	}

	internal func updateVisibleRows(visibleRows: [NSIndexPath]) {
		if visibleRows.count > 0 {
			collectionView!.reloadItemsAtIndexPaths(visibleRows)
		}
		else {
			collectionView!.reloadData()
		}
	}

	internal func turnStreamModeOn() {
		moreRows = true
		(screenlet as? BaseListScreenlet)?.streamMode = true
	}
}
