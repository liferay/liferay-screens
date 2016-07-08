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

#if LIFERAY_SCREENS_FRAMEWORK
	import ODRefreshControl
#endif


public class BaseListTableView: BaseListView, UITableViewDataSource, UITableViewDelegate {
	
	@IBOutlet public var tableView: UITableView?
	
	internal var refreshControlView: ODRefreshControl?
	
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
		
		tableView?.delegate = self
		tableView?.dataSource = self
		
		doRegisterCellNibs()
	}
	
	override public func onChangedRows(oldRows: [AnyObject?]) {
		super.onChangedRows(oldRows)
		
		if self.rows.isEmpty {
			clearAllRows(oldRows)
		}
		else if oldRows.isEmpty {
			insertFreshRows()
		}
		else if let visibleRows = tableView!.indexPathsForVisibleRows {
			//We have added rows since row count computation
			if rows.count != oldRows.count {
				moreRows = true
				streamMode = true
				(screenlet as? BaseListScreenlet)?.streamMode = true
				onAddedRows(lastCount: oldRows.count)
			}
			updateVisibleRows(visibleRows)
		}
		else {
			tableView!.reloadData()
		}
	}
	
	override public func onAddedRows(lastCount lastCount: Int) {
		insertRows(from: lastCount, to: rows.count)
		
		if moreRows {
			showProgressFooter()
		}
		else {
			hideProgressFooter()
		}
	}
	
	public override func onClearRows(oldRows: [AnyObject?]) {
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
	
	public func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return rows.count
	}
	
	public func tableView(tableView: UITableView,
	                      cellForRowAtIndexPath
		indexPath: NSIndexPath)
		-> UITableViewCell {
			let object: AnyObject? = rows[indexPath.row]
			let cell = doDequeueReusableCell(row: indexPath.row, object: object)
			
			if let object = object {
				doFillLoadedCell(row: indexPath.row, cell: cell, object: object)
			}
			else {
				doFillInProgressCell(row: indexPath.row, cell: cell)
				if !streamMode {
					fetchPageForRow?(indexPath.row)
				}
			}
			
			return cell
	}
	
	public func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
		tableView.deselectRowAtIndexPath(indexPath, animated: false)
		
		if let row:AnyObject = rows[indexPath.row] {
			onSelectedRowClosure?(row)
		}
	}
	
	public func tableView(tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: NSIndexPath) {
		if streamMode && !loadingRows && moreRows {
			if indexPath.row == rows.count - 1 {
				loadingRows = true
				fetchPageForRow?(indexPath.row + 1)
			}
		}
	}
	
	public func doDequeueReusableCell(row row: Int, object: AnyObject?) -> UITableViewCell {
		let cellId = doGetCellId(row: row, object: object)
		
		guard let result = tableView!.dequeueReusableCellWithIdentifier(cellId) else {
			return doCreateCell(cellId)
		}
		
		return result
	}
	
	public func doFillLoadedCell(row row: Int, cell: UITableViewCell, object:AnyObject) {
	}
	
	public func doFillInProgressCell(row row: Int, cell: UITableViewCell) {
	}
	
	public func doRegisterCellNibs() {
	}
	
	public func doGetCellId(row row: Int, object: AnyObject?) -> String {
		return "defaultCellId"
	}
	
	public func doCreateCell(cellId: String) -> UITableViewCell {
		return UITableViewCell(style: .Default, reuseIdentifier: cellId)
	}
	
	public func createLoadingMoreView() -> UIView? {
		let progressView = UIView(frame: CGRect(x: 0, y: 0, width: frame.width, height: 30))
		
		let indicatorView = UIActivityIndicatorView(activityIndicatorStyle: .Gray)
		indicatorView.center = CGPoint(x: frame.width/2, y: indicatorView.center.y)
		indicatorView.startAnimating()
		
		progressView.addSubview(indicatorView)
		
		return progressView
	}
	
	
	//MARK: Internal methods
	
	internal func updateRefreshControl() {
		if refreshClosure != nil {
			if refreshControlView == nil {
				refreshControlView = ODRefreshControl(
					inScrollView: self.tableView)
				refreshControlView!.addTarget(self,
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
	
	internal func refreshControlBeginRefresh(sender:AnyObject?) {
		dispatch_delayed(0.3) {
			self.moreRows = true
			self.hideProgressFooter()
			self.refreshClosure?()
		}
	}
	
	internal func insertFreshRows() {
		insertRows(from: 0, to: rows.count)
	}
	
	internal func insertRows(from from: Int, to: Int) {
		let indexPaths = (from..<to).map {
			NSIndexPath(forRow: $0, inSection: 0)
		}
		tableView!.beginUpdates()
		tableView!.insertRowsAtIndexPaths(indexPaths, withRowAnimation:.Top)
		tableView!.endUpdates()
	}
	
	internal func clearAllRows(currentRows: [AnyObject?]) {
		tableView!.beginUpdates()
		
		for (index,_) in currentRows.enumerate() {
			let indexPath = NSIndexPath(forRow:index, inSection:0)
			tableView!.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
		}
		
		tableView!.endUpdates()
	}
	
	internal func updateVisibleRows(visibleRows: [NSIndexPath]) {
		if visibleRows.count > 0 {
			tableView!.reloadRowsAtIndexPaths(visibleRows, withRowAnimation:.None)
		}
		else {
			tableView!.reloadData()
		}
	}
	
	internal func showProgressFooter() {
		tableView?.tableFooterView = createLoadingMoreView()
	}
	
	internal func hideProgressFooter() {
		tableView?.tableFooterView = nil
	}
	
}
