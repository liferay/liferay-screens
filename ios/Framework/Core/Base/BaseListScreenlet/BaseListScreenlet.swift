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


public class BaseListScreenlet: BaseScreenlet {
	
	public class var LoadInitialPageAction: String { return "load-initial-page" }
	public class var LoadPageAction: String { return "load-page" }


	//MARK: Inspectables
	
	@IBInspectable public var autoLoad: Bool = true
	
	@IBInspectable public var refreshControl: Bool = true {
		didSet {
			updateRefreshClosure()
		}
	}
	
	@IBInspectable public var firstPageSize: Int = 50

	@IBInspectable public var pageSize: Int = 25

	@IBInspectable public var obcClassName: String = ""
	
	public var baseListView: BaseListView {
		return screenletView as! BaseListView
	}
	
	internal var streamMode = false
	
	private var paginationInteractors: [Int:BaseListPageLoadInteractor] = [:]
	
	
	//MARK: BaseScreenlet
	
	override public func onCreated() {
		baseListView.onSelectedRowClosure = onSelectedRow
		baseListView.fetchPageForRow = loadPageForRow

		updateRefreshClosure()
	}
	
	override public func onShow() {
		if !isRunningOnInterfaceBuilder {
			if autoLoad {
				loadList()
			}
		}
	}
	
	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		let page = (sender as? Int) ?? 0

		let interactor = createPageLoadInteractor(
			page: page,
			computeRowCount: (page == 0))
		
		
		paginationInteractors[page] = interactor

		interactor.obcClassName = (obcClassName == "") ? nil : obcClassName
		
		interactor.onSuccess = {
			self.baseListView.setRows(interactor.resultAllPagesContent!, newRows: interactor.resultPageContent!,
			                          rowCount: interactor.resultRowCount ?? self.baseListView.rowCount,
			                          sections: interactor.sections ?? [BaseListView.DefaultSection])

			self.onLoadPageResult(
				page: interactor.page,
				rows: interactor.resultPageContent?.map {$1}.flatMap {$0} ?? [],
				rowCount: self.baseListView.rowCount)
			
			self.paginationInteractors.removeValueForKey(interactor.page)
		}
		
		interactor.onFailure = {
			self.onLoadPageError(page: interactor.page, error: $0)
			
			self.paginationInteractors.removeValueForKey(interactor.page)
		}
		
		return interactor
	}
	
	override public func onAction(name name: String, interactor: Interactor, sender: AnyObject?) -> Bool {
		
		if name == BaseListScreenlet.LoadInitialPageAction {
			// clear list while it's loading
			self.baseListView.clearRows()
		}
		
		return super.onAction(name: name, interactor: interactor, sender: sender)
	}
	
	
	//MARK: Public methods
	
	/// Starts the request to load the list of records.
	/// The list is shown when the response is received.
	///
	/// - Returns: true if the request is sent.
	public func loadList() -> Bool {
		//by default we start in fluent mode
		streamMode = false
		return performAction(name: BaseListScreenlet.LoadInitialPageAction, sender: nil)
	}
	
	/// Start the request to load the proper page for the given row.
	///
	/// - Parameter row: item row.
	public func loadPageForRow(row: Int) {
		let page = pageFromRow(row)
		
		// make sure we don't create two interactors for the same page
		synchronized(paginationInteractors) {
			if self.paginationInteractors.indexForKey(page) == nil {
				
				self.performAction(name: BaseListScreenlet.LoadPageAction, sender: page)
			}
		}
	}
	
	/// Gets the page from the given row.
	///
	/// - Parameter row: item row.
	/// - Returns: item page.
	public func pageFromRow(row: Int) -> Int {
		if row < firstPageSize {
			return 0
		}
		
		return ((row - firstPageSize) / pageSize) + 1
	}
	
	/// Gets the first row for the given page.
	///
	/// - Parameter page: page number.
	/// - Returns: item row.
	public func firstRowForPage(page: Int) -> Int {
		if page == 0 {
			return 0
		}
		
		return firstPageSize + (page - 1) * pageSize
	}
	
	
	/// Prepares the page to be loaded in the list.
	/// This method is intended to be overwritten by children classes.
	///
	/// - Parameters:
	///   - page: page number.
	///   - computeRowCount: true if we want to compute row count.
	/// - Returns: proper interactor.
	public func createPageLoadInteractor(
			page page: Int,
			computeRowCount: Bool) -> BaseListPageLoadInteractor {
			
		fatalError("createPageLoadInteractor must be overriden")
	}
	
	/// Shows the error during page loading.
	/// This method is intended to be overwritten by children classes.
	///
	/// - Parameters:
	///   - page: page number.
	///   - error: error on loading.
	public func onLoadPageError(page page: Int, error: NSError) {
		print("ERROR: Load page error \(page) -> \(error)\n")
	}
	
	/// Gets the information about one page results.
	/// Call this method if you want to render the results.
	///
	/// - Parameters:
	///   - page: page number.
	///   - rows: page items.
	///   - rowCount: row count.
	public func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
	}
	
	/// Gets the information about one row.
	/// Call this method if you want to know what item list was selected.
	///
	/// - Parameter row: selected row.
	public func onSelectedRow(row:AnyObject) {
	}


	//MARK: Internal methods

	internal func updateRefreshClosure() {

		let refreshClosure: (Void -> Bool)? = refreshControl ? self.loadList : nil

		if let screenletView = screenletView as? BaseListTableView {
			screenletView.refreshClosure = refreshClosure
		}
		else if let screenletView = screenletView as? BaseListCollectionView {
			screenletView.refreshClosure = refreshClosure
		}
	}
}
