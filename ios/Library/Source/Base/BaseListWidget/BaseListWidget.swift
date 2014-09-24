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


@IBDesignable public class BaseListWidget: BaseWidget {

	@IBInspectable public var firstPageSize = 5
	@IBInspectable public var pageSize = 2

	internal var baseListView: BaseListView {
		return widgetView as BaseListView
	}

	private var paginationOperations: [Int:PaginationOperation] = [:]


	//MARK: BaseWidget

	override public func onCreated() {
		baseListView.onSelectedRowClosure = onSelectedRow
		baseListView.fetchPageForRow = loadPageForRow
	}


	//MARK: Public methods

	public func loadList() -> Bool {
		if SessionContext.instance.hasSession {
			println("ERROR: No session initialized. Can't load the list without session")
			return false
		}

		startOperationWithMessage("Loading list...", details:"Wait few seconds...")

		return loadPage(0)
	}


	//MARK: Internal methods

	internal func loadPage(page:Int) -> Bool {
		let operation = PaginationOperation(page:page)

		operation.onOperationSuccess = onLoadPageResult
		operation.onOperationFailure = onLoadPageError

		paginationOperations[page] = operation

		let session = SessionContext.instance.createBatchSessionFromCurrentSession()!
		session.callback = operation

		doGetPageRowsOperation(session: session, page: page)

		if session.commands.count < 1 {
			println("Error: Get page rows operation couldn't be started")

			return false
		}

		doGetRowCountOperation(session: session)

		if session.commands.count < 2 {
			println("Error: Get row count operation couldn't be started")

			return false
		}

		var outError: NSError?

		session.invoke(&outError)

		if let error = outError {
			operation.onFailure(error)

			return false
		}

		return true
	}

	internal func doGetPageRowsOperation(#session: LRSession, page: Int){
	}

	internal func doGetRowCountOperation(#session: LRSession) {
	}

	internal func convert(#serverResult:[String:AnyObject]) -> AnyObject {
		return 0
	}

	internal func loadPageForRow(row:Int) {
		let page = pageFromRow(row)

		if paginationOperations.indexForKey(page) == nil {
			loadPage(page)
		}
	}

	internal func pageFromRow(row:Int) -> Int {
		if row < firstPageSize {
			return 0
		}

		return ((row - firstPageSize) / pageSize) + 1
	}

	internal func firstRowForPage(page:Int) -> Int {
		if page == 0 {
			return 0
		}

		return firstPageSize + (page - 1) * pageSize
	}

	internal func onLoadPageError(page: Int, error: NSError) {
		if page == 0 {
			finishOperationWithError(error, message:"Error getting list!")
		}

		paginationOperations.removeValueForKey(page)
	}

	internal func onLoadPageResult(page: Int,
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

		baseListView.rowCount = rowCount
		baseListView.rows = allRows

		if page == 0 {
			finishOperation()
		}

		paginationOperations.removeValueForKey(page)

		return convertedRows
	}

	internal func onSelectedRow(row:AnyObject) {
	}

}
