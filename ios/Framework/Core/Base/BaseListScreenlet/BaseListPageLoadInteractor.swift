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


public class BaseListPageLoadInteractor: ServerReadConnectorInteractor {
	
	public let page: Int
	public let computeRowCount: Bool
	
	public var resultAllPagesContent: [String : [AnyObject?]]?
	public var resultPageContent: [String : [AnyObject]]?
	public var resultRowCount: Int?
	public var sections: [String]?
	public var streamMode = false
	
	
	
	public init(screenlet: BaseListScreenlet, page: Int, computeRowCount: Bool) {
		self.page = page
		self.computeRowCount = computeRowCount
		super.init(screenlet: screenlet)
	}
	
	override public func createConnector() -> PaginationLiferayConnector {
		fatalError("createConnector must be overriden")
	}
	
	override public func completedConnector(op: ServerConnector) {
		if let pageOp = op as? PaginationLiferayConnector {
			processLoadPageResult(pageOp.resultPageContent ?? [], rowCount: pageOp.resultRowCount)
		}
	}
	
	public func processLoadPageResult(serverRows: [[String : AnyObject]], rowCount: Int?) {
		let screenlet = self.screenlet as! BaseListScreenlet
		let baseListView = screenlet.screenletView as! BaseListView
		
		let actualRowCount = rowCount ?? baseListView.rows[BaseListView.DefaultSection]!.count
		
		let convertedRows = serverRows.map() { self.convertResult($0) }
		
		var allRows = baseListView.rows
		var convertedRowsWithSection = [String : [AnyObject]]()
		var sections = baseListView.sections
		
		//Fill sections 
		
		for obj in convertedRows {
			let sectionName = sectionForRowObject(obj) ?? BaseListView.DefaultSection
			
			if convertedRowsWithSection.indexForKey(sectionName) == nil {
				convertedRowsWithSection[sectionName] = [AnyObject]()
				
				if !sections.contains(sectionName) {
					sections.append(sectionName)
				}
			}
			
			convertedRowsWithSection[sectionName]!.append(obj)
		}
		
		if (sections.count > 1 || rowCount == nil) && page == 0 {
			streamMode = true
		}
		
		if streamMode {
			allRows = baseListView.rows
		
			for section in convertedRowsWithSection.keys {
				if allRows.indexForKey(section) == nil {
					allRows[section] = [AnyObject?]()
				}
				
				let rowsInSection = convertedRowsWithSection[section]!
				
				for row in rowsInSection {
					allRows[section]!.append(row)
				}
			}
		}
		else {
			//If we reach this point we will have only one section with key ""
			allRows[BaseListView.DefaultSection] = Array<AnyObject?>(count: actualRowCount, repeatedValue: nil)
			
			//Insert existing elements in the list
			for (index, row) in baseListView.rows[BaseListView.DefaultSection]!.enumerate() {
				allRows[BaseListView.DefaultSection]![index] = row
			}
			
			var offset = screenlet.firstRowForPage(page)
			var lastIndex = 0
			
			//Insert new elements
			for (index, row) in convertedRows.enumerate() {
				if index + offset < actualRowCount {
					allRows[BaseListView.DefaultSection]![index + offset] = row
					lastIndex = index + offset
				}
				else {
					allRows[BaseListView.DefaultSection]!.append(row)
				}
			}
			
			//Deleted elements since row computation
			if lastIndex+1 < actualRowCount && !streamMode && convertedRows.count < screenlet.pageSize && page != 0 {
				for _ in lastIndex+1..<actualRowCount {
					allRows[BaseListView.DefaultSection]!.popLast()
				}
			}
		}
		
		self.resultRowCount = actualRowCount
		self.resultPageContent = convertedRowsWithSection
		self.resultAllPagesContent = allRows
		self.sections = sections
	}
	
	public func convertResult(serverResult: [String:AnyObject]) -> AnyObject {
		fatalError("convert(serverResult) must be overriden")
	}
	
	public func sectionForRowObject(object: AnyObject) -> String? {
		return nil
	}
	
	//MARK: Cache
	
	override public func readFromCache(op: ServerConnector, result: AnyObject? -> ()) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}
		
		if let loadOp = op as? PaginationLiferayConnector {
			let key = cacheKey(loadOp)
			cacheManager.getSome(
				collection: ScreenletName(screenlet!.dynamicType),
				keys: ["\(key)-\(page)", "\(key)-\(page)-count"]) {
					
					loadOp.resultPageContent = $0.first as? [[String:AnyObject]]
					if $0.count > 1 {
						loadOp.resultRowCount = $0.last as? Int
					}
					
					result(loadOp.resultPageContent)
			}
		}
		else {
			result(nil)
		}
	}
	
	override public func writeToCache(op: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}
		
		if let loadOp = op as? PaginationLiferayConnector,
			pageContent = loadOp.resultPageContent
			where !pageContent.isEmpty {
			
			let key = cacheKey(loadOp)
			
			cacheManager.setClean(
				collection: ScreenletName(screenlet!.dynamicType),
				key: "\(key)-\(page)",
				value: pageContent,
				attributes: [:])
			
			if let rowCount = loadOp.resultRowCount {
				cacheManager.setClean(
					collection: ScreenletName(screenlet!.dynamicType),
					key: "\(key)-\(page)-count",
					value: rowCount,
					attributes: [:])
			}
		}
	}
	
	public func cacheKey(op: PaginationLiferayConnector) -> String {
		fatalError("cacheKey must be overriden")
	}
	
}
