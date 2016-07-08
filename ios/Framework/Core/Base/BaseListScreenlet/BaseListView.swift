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


public class BaseListView: BaseScreenletView {
	
	public var streamMode: Bool = false
	
	public var rowCount: Int {
		return _rowCount
	}
	
	public var rows: [AnyObject?] {
		return _rows
	}
	
	public var onSelectedRowClosure: (AnyObject -> Void)?
	public var fetchPageForRow: (Int -> Void)?
	
	private var _rows = [AnyObject?]()
	private var _rowCount = 0
	
	public var loadingRows = false
	public var moreRows = true
	
	public func setRows(allRows: [AnyObject?], newRows: [AnyObject], rowCount: Int) {
		loadingRows = false
		
		if newRows.count == 0 || newRows.count < (screenlet as? BaseListScreenlet)?.pageSize {
			moreRows = false
		}
		_rowCount = rowCount
		
		let oldRows = _rows
		_rows = allRows
		
		if streamMode {
			onAddedRows(lastCount: oldRows.count)
		} else {
			onChangedRows(oldRows)
		}
	}
	
	public func clearRows() {
		let oldRows = _rows
		_rows = [AnyObject?]()
		_rowCount = 0
		
		onClearRows(oldRows)
	}
	
	public func onChangedRows(oldRows:[AnyObject?]) {
	}
	
	public func onAddedRows(lastCount lastCount: Int) {
		
	}
	
	public func onClearRows(oldRows:[AnyObject?]) {
		
	}
	
}
