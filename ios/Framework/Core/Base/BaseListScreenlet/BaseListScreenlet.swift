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
					refreshControl ? self.refreshList : nil
		}
	}

	@IBInspectable public var firstPageSize: Int = 50
	@IBInspectable public var pageSize: Int = 25

	internal var baseListView: BaseListView {
		return screenletView as! BaseListView
	}

	private var paginationInteractors: [Int:BaseListPageLoadInteractor] = [:]


	//MARK: BaseScreenlet

	override public func onCreated() {
		baseListView.onSelectedRowClosure = onSelectedRow
		baseListView.fetchPageForRow = loadPageForRow

		(screenletView as? BaseListTableView)?.refreshClosure =
				refreshControl ? self.refreshList : nil
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
			self.baseListView.setRows(interactor.resultAllPagesContent!,
				rowCount: interactor.resultRowCount ?? self.baseListView.rowCount)

			self.onLoadPageResult(
				page: interactor.page,
				rows: interactor.resultPageContent ?? [],
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

		let result = super.onAction(name: name, interactor: interactor, sender: sender)

		if result && name == BaseListScreenlet.LoadInitialPageAction {
			self.baseListView.setRows([], rowCount:0)
		}

		return result
	}


	//MARK: Public methods

	public func loadList() -> Bool {
		return performAction(name: BaseListScreenlet.LoadInitialPageAction, sender: nil)
	}

	public func refreshList() -> Bool {
		return performAction(name: BaseListScreenlet.LoadPageAction, sender: 0)
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


	//MARK: Internal methods

	internal func createPageLoadInteractor(
			page page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		fatalError("createPageLoadInteractor must be overriden")
	}

	internal func onLoadPageError(page page: Int, error: NSError) {
		print("ERROR: Load page error \(page) -> \(error)\n")
	}

	internal func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
	}

	internal func onSelectedRow(row:AnyObject) {
	}

}
