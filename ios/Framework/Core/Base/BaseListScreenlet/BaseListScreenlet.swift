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


@IBDesignable public class BaseListScreenlet: BaseScreenlet {
	
	public class var LoadInitialPageAction: String { return "load-initial-page" }
	public class var LoadPageAction: String { return "load-page" }
	
	
	@IBInspectable public var autoLoad: Bool = true
	
	@IBInspectable public var refreshControl: Bool = true {
		didSet {
			(screenletView as? BaseListTableView)?.refreshClosure =
				refreshControl ? self.loadList : nil
		}
	}
	
	@IBInspectable public var firstPageSize: Int = 50
	@IBInspectable public var pageSize: Int = 25
	
	public var baseListView: BaseListView {
		return screenletView as! BaseListView
	}
	
	internal var streamMode = false
	
	private var paginationInteractors: [Int:BaseListPageLoadInteractor] = [:]
	
	
	//MARK: BaseScreenlet
	
	override public func onCreated() {
		baseListView.onSelectedRowClosure = onSelectedRow
		baseListView.fetchPageForRow = loadPageForRow
		
		(screenletView as? BaseListTableView)?.refreshClosure =
			refreshControl ? self.loadList : nil
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
	
	public func loadList() -> Bool {
		//by default we start in fluent mode
		streamMode = false
		return performAction(name: BaseListScreenlet.LoadInitialPageAction, sender: nil)
	}
	
	public func loadPageForRow(row: Int) {
		let page = pageFromRow(row)
		
		// make sure we don't create two interactors for the same page
		synchronized(paginationInteractors) {
			if self.paginationInteractors.indexForKey(page) == nil {
				
				self.performAction(name: BaseListScreenlet.LoadPageAction, sender: page)
			}
		}
	}
	
	public func pageFromRow(row: Int) -> Int {
		if row < firstPageSize {
			return 0
		}
		
		return ((row - firstPageSize) / pageSize) + 1
	}
	
	public func firstRowForPage(page: Int) -> Int {
		if page == 0 {
			return 0
		}
		
		return firstPageSize + (page - 1) * pageSize
	}
	
	
	public func createPageLoadInteractor(
		page page: Int,
		     computeRowCount: Bool)
		-> BaseListPageLoadInteractor {
			
			fatalError("createPageLoadInteractor must be overriden")
	}
	
	public func onLoadPageError(page page: Int, error: NSError) {
		print("ERROR: Load page error \(page) -> \(error)\n")
	}
	
	public func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
	}
	
	public func onSelectedRow(row:AnyObject) {
	}
	
}
