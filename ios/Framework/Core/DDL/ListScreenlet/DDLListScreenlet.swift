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


@objc public protocol DDLListScreenletDelegate : BaseScreenletDelegate {

	@objc optional func screenlet(_ screenlet: DDLListScreenlet,
			onDDLListResponseRecords records: [DDLRecord])

	@objc optional func screenlet(_ screenlet: DDLListScreenlet,
			onDDLListError error: NSError)

	@objc optional func screenlet(_ screenlet: DDLListScreenlet,
			onDDLSelectedRecord record: DDLRecord)

}


open class DDLListScreenlet: BaseListScreenlet {


	//MARK: Inspectables
	
	@IBInspectable open var userId: Int64 = 0

	@IBInspectable open var recordSetId: Int64 = 0

	@IBInspectable open var labelFields: String? {
		didSet {
			(screenletView as? DDLListViewModel)?.labelFields = parseFields(labelFields)
		}
	}

	@IBInspectable open var offlinePolicy: String? = CacheStrategyType.remoteFirst.rawValue


	open var ddlListDelegate: DDLListScreenletDelegate? {
		return delegate as? DDLListScreenletDelegate
	}

	open var viewModel: DDLListViewModel {
		return screenletView as! DDLListViewModel
	}


	//MARK: BaseListScreenlet

	override open func onCreated() {
		super.onCreated()

		viewModel.labelFields = parseFields(self.labelFields)
	}

	override open func createPageLoadInteractor(
			page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		let interactor = DDLListPageLoadInteractor(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount,
				userId: self.userId,
				recordSetId: self.recordSetId)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .remoteFirst

		return interactor
	}

	override open func onLoadPageError(page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		ddlListDelegate?.screenlet?(self, onDDLListError: error)
	}

	override open func onLoadPageResult(page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		ddlListDelegate?.screenlet?(self,
				onDDLListResponseRecords: rows as! [DDLRecord])
	}

	override open func onSelectedRow(_ row: AnyObject) {
		ddlListDelegate?.screenlet?(self,
				onDDLSelectedRecord: row as! DDLRecord)
	}


	//MARK: Private methods

	fileprivate func parseFields(_ fields: String?) -> [String] {
		var result: [String] = []

		if let fieldsValue = fields {
			let dirtyFields = (fieldsValue as NSString).components(separatedBy: ",")
			result = dirtyFields.map() {
				$0.trimmingCharacters(in: .whitespaces)
			}
			result = result.filter() { return $0 != "" }
		}

		return result
	}

}
