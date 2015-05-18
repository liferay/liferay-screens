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

	@IBInspectable public var autoLoad: Bool = true

	@IBInspectable public var refreshControl: Bool = true {
		didSet {
			(screenletView as? BaseListTableView)?.refreshClosure =
					refreshControl ? self.loadList : nil
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
				refreshControl ? self.loadList : nil
	}

	override public func onShow() {
		if !isRunningOnInterfaceBuilder {
			if autoLoad {
				loadList()
			}
		}
	}


	//MARK: Public methods

	public func loadList() -> Bool {
		let result = startLoadPageInteractor(page: 0, computeRowCount: true)

		if result {
			self.baseListView.setRows([], rowCount:0)
		}

		return result
	}


	//MARK: Internal methods

	internal func pageFromRow(row: Int) -> Int {
		if row < firstPageSize {
			return 0
		}

		return ((row - firstPageSize) / pageSize) + 1
	}

	internal func firstRowForPage(page: Int) -> Int {
		if page == 0 {
			return 0
		}

		return firstPageSize + (page - 1) * pageSize
	}

	internal func startLoadPageInteractor(#page: Int, computeRowCount: Bool = false) -> Bool {
		let interactor = createPageLoadInteractor(
				page: page,
				computeRowCount: computeRowCount)

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

		return interactor.start()
	}

	internal func createPageLoadInteractor(
			#page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		assertionFailure("createPageLoadInteractor must be overriden")

		return BaseListPageLoadInteractor(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount)
	}

	internal func loadPageForRow(row: Int) {
		let page = pageFromRow(row)

		// make sure we don't create two interactors for the same page
		synchronized(paginationInteractors) {
			if self.paginationInteractors.indexForKey(page) == nil {
				self.startLoadPageInteractor(page: page)
			}
		}
	}

	internal func onLoadPageError(#page: Int, error: NSError) {
		println("ERROR: Load page error \(page) -> \(error)")
	}

	internal func onLoadPageResult(#page: Int, rows: [AnyObject], rowCount: Int) {
	}

	internal func onSelectedRow(row:AnyObject) {
	}

}
