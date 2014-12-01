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

	@IBInspectable public var firstPageSize: Int = 50
	@IBInspectable public var pageSize: Int = 25

	internal var baseListView: BaseListView {
		return screenletView as BaseListView
	}

	private var paginationOperations: [Int:LiferayPaginationOperation] = [:]
	private var rowCount: Int = 0


	//MARK: BaseScreenlet

	override public func onCreated() {
		baseListView.onSelectedRowClosure = onSelectedRow
		baseListView.fetchPageForRow = loadPageForRow
	}

	override func onShow() {
		if autoLoad {
			loadList()
		}
	}


	//MARK: Public methods

	public func loadList() -> Bool {
		return loadPage(0, computeRowCount: true)
	}


	//MARK: Internal methods

	internal func loadPage(page: Int, computeRowCount: Bool = false) -> Bool {
		let operation = createPaginationOperation(
				page: page,
				computeRowCount: computeRowCount)

		paginationOperations[page] = operation

		return operation.validateAndEnqueue() {
			if $0.lastError == nil {
				self.onLoadPageResult(
						page: operation.page,
						serverRows: operation.result!.pageContent,
						rowCount: operation.result!.rowCount ?? self.rowCount)
			}
			else {
				self.onLoadPageError(page: operation.page, error: $0.lastError!)
			}

			self.paginationOperations.removeValueForKey(operation.page)
		}
	}

	internal func createPaginationOperation(
			#page: Int,
			computeRowCount: Bool)
			-> LiferayPaginationOperation {

		assertionFailure("createPaginationOperation must be overriden")

		return LiferayPaginationOperation(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount)
	}

	internal func convert(#serverResult:[String:AnyObject]) -> AnyObject {
		assertionFailure("convert(serverResult) must be overriden")
		return 0
	}

	internal func loadPageForRow(row: Int) {
		let page = pageFromRow(row)

		if paginationOperations.indexForKey(page) == nil {
			loadPage(page)
		}
	}

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

	internal func onLoadPageError(#page: Int, error: NSError) {
	}

	internal func onLoadPageResult(#page: Int,
			serverRows: [[String:AnyObject]],
			rowCount: Int)
			-> [AnyObject] {

		let convertedRows = serverRows.map() { self.convert(serverResult: $0) }

		var allRows = Array<AnyObject?>(count: rowCount, repeatedValue: nil)

		for (index, row) in enumerate(baseListView.rows) {
			allRows[index] = row
		}

		var offset = (page == 0) ? 0 : firstPageSize + (page - 1) * pageSize

		// last page could be incomplete
		if offset >= rowCount {
			offset = rowCount - 1
		}

		for (index, row) in enumerate(convertedRows) {
			allRows[offset + index] = row
		}

		self.rowCount = rowCount
		baseListView.rowCount = rowCount
		baseListView.rows = allRows

		return convertedRows
	}

	internal func onSelectedRow(row:AnyObject) {
	}

}
