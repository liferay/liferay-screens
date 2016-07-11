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
	
	public static let DefaultSection = ""
	
	public var streamMode: Bool = false
	
	public var rowCount: Int {
		return _rowCount
	}
	
	public var rows: [String : [AnyObject?]] {
		return _rows
	}
	
	public var sections: [String] {
		return _sections
	}
	
	public var onSelectedRowClosure: (AnyObject -> Void)?
	public var fetchPageForRow: (Int -> Void)?
	
	private var _rows = [String : [AnyObject?]]()
	private var _sections = [String]()
	private var _rowCount = 0
	
	public var loadingRows = false
	public var moreRows = true
	
	public func setRows(allRows: [String:[AnyObject?]], newRows: [String:[AnyObject]], rowCount: Int,
	                    sections: [String]) {
		
		loadingRows = false
		
		if newRows.count == 0 {
			moreRows = false
		}
		_rowCount = rowCount
		
		let oldRows = _rows
		_rows = allRows
		
		_sections = sections
		
		if streamMode {
			onAddedRows(lastCount: oldRows.count)
		} else {
			onChangedRows(oldRows)
		}
	}
	
	public func clearRows() {
		let oldRows = _rows
		_rows = [String : [AnyObject?]]()
		_rows[BaseListView.DefaultSection] = [AnyObject?]()
		_rowCount = 0
		_sections = [String]()
		
		onClearRows(oldRows)
	}
	
	public func onChangedRows(oldRows:[String : [AnyObject?]]) {
	}
	
	public func onAddedRows(lastCount lastCount: Int) {
		
	}
	
	public func onClearRows(oldRows:[String : [AnyObject?]]) {
		
	}
	
	internal func rowsForSectionIndex(index: Int) -> [AnyObject?] {
		let key = sections[index]
		
		return rows[key]!
	}
}
