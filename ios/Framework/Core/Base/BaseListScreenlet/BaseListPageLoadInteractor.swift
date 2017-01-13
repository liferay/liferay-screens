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

	public var obcClassName: String?
	
	public var resultAllPagesContent: [String : [AnyObject?]]?

	public var resultPageContent: [String : [AnyObject]]?

	public var resultRowCount: Int?

	public var sections: [String]?
	

	//MARK: Initializers
	
	public init(screenlet: BaseListScreenlet, page: Int, computeRowCount: Bool) {
		self.page = page
		self.computeRowCount = computeRowCount
		super.init(screenlet: screenlet)
	}


	//MARK: ServerConnectorInteractor

	override public func createConnector() -> PaginationLiferayConnector {
		let connector = createListPageConnector()

		connector.obcClassName = self.obcClassName

		return connector
	}
	
	override public func completedConnector(c: ServerConnector) {
		if let pageCon = c as? PaginationLiferayConnector {
			processLoadPageResult(pageCon.resultPageContent ?? [], rowCount: pageCon.resultRowCount)
		}
	}


	//MARK: Public methods

	public func createListPageConnector() -> PaginationLiferayConnector {
		fatalError("createListPageConnector must be overriden")
	}
	
	public func processLoadPageResult(serverRows: [[String : AnyObject]], rowCount: Int?) {
		let screenlet = self.screenlet as! BaseListScreenlet
		let baseListView = screenlet.screenletView as! BaseListView
		
		let actualRowCount = rowCount ?? baseListView.rows[BaseListView.DefaultSection]!.count
		
		let convertedRows = serverRows.map() { self.convertResult($0) }
		
		var allRows = baseListView.rows
		var convertedRowsWithSection = [String : [AnyObject]]()
		var sections = baseListView.sections
		
		let isFirstPage = (page == 0)
		let isPageFull = isFirstPage
				? (convertedRows.count == screenlet.firstPageSize)
				: (convertedRows.count == screenlet.pageSize)
		
		var hasSections = (sections.count > 1) || (sections.count == 1 && sections.first != BaseListView.DefaultSection)
		
		// Fill sections
		if isFirstPage || hasSections  {
			
			// Group rows loop
			for obj in convertedRows {
				let sectionName = sectionForRowObject(obj) ?? BaseListView.DefaultSection
				
				if convertedRowsWithSection.indexForKey(sectionName) == nil {
					convertedRowsWithSection[sectionName] = [AnyObject]()
					
					if !sections.contains(sectionName) {
						sections.append(sectionName)
						
						if sectionName != BaseListView.DefaultSection {
							hasSections = true
						}
					}
				}
				
				if hasSections && sectionName == BaseListView.DefaultSection {
					print("ERROR: you returned mixed empty and non-empty sections in sectionForRowObject()")
				}
				
				convertedRowsWithSection[sectionName]!.append(obj)
			}
		}
		else {
			// Without sections simply assign incoming rows to the default section
			convertedRowsWithSection[BaseListView.DefaultSection] = convertedRows
		}
		
		// StreamMode is only decided by the interactor in the first page load
		// otherwise this state could be changed for other interactors
		if isFirstPage && (hasSections || rowCount == nil) {
			screenlet.streamMode = true
		}
		
		//Fill rows
		if screenlet.streamMode {
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
			
			let offset = screenlet.firstRowForPage(page)
			var lastIndexInserted = 0
			
			//Insert new elements
			for (index, row) in convertedRows.enumerate() {
				if index + offset < actualRowCount {
					allRows[BaseListView.DefaultSection]![index + offset] = row
					lastIndexInserted = index + offset
				}
				else {
					allRows[BaseListView.DefaultSection]!.append(row)
				}
			}
			
			let lessItemsThanExpected = (lastIndexInserted + 1 < actualRowCount)
			let incompleteMiddlePage = (!isPageFull && !isFirstPage)
			
			let streamMode = screenlet.streamMode
			
			//Deleted elements since row count computation
			if lessItemsThanExpected && !streamMode && incompleteMiddlePage {
				for _ in lastIndexInserted+1..<actualRowCount {
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

	public func cacheKey(c: PaginationLiferayConnector) -> String {
		fatalError("cacheKey must be overriden")
	}
	
	//MARK: Cache methods
	
	override public func readFromCache(c: ServerConnector, result: AnyObject? -> ()) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			result(nil)
			return
		}
		
		if let loadCon = c as? PaginationLiferayConnector {
			let key = cacheKey(loadCon)
			cacheManager.getSome(
				collection: ScreenletName(screenlet!.dynamicType),
				keys: ["\(key)-\(page)", "\(key)-count"]) {
					
					loadCon.resultPageContent = $0.first as? [[String:AnyObject]]
					if $0.count > 1 {
						loadCon.resultRowCount = $0.last as? Int
					}
					
					result(loadCon.resultPageContent)
			}
		}
		else {
			result(nil)
		}
	}
	
	override public func writeToCache(c: ServerConnector) {
		guard let cacheManager = SessionContext.currentContext?.cacheManager else {
			return
		}
		
		if let loadCon = c as? PaginationLiferayConnector,
			pageContent = loadCon.resultPageContent
			where !pageContent.isEmpty {
			
			let key = cacheKey(loadCon)
			
			cacheManager.setClean(
				collection: ScreenletName(screenlet!.dynamicType),
				key: "\(key)-\(page)",
				value: pageContent,
				attributes: [:])
			
			if let rowCount = loadCon.resultRowCount {
				cacheManager.setClean(
					collection: ScreenletName(screenlet!.dynamicType),
					key: "\(key)-count",
					value: rowCount,
					attributes: [:])
			}
		}
	}
	
}
