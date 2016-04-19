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


@objc public protocol WebContentListScreenletDelegate : BaseScreenletDelegate {

	optional func screenlet(screenlet: WebContentListScreenlet,
			onWebContentListResponseEntries entries: [WebContentEntry])

	optional func screenlet(screenlet: WebContentListScreenlet,
			onWebContentListError error: NSError)

	optional func screenlet(screenlet: WebContentListScreenlet,
			onWebContentSelectedEntry entry: WebContentEntry)

}


@objc public class WebContentEntry : AssetListScreenletEntry {

	public let structure: DDMStructure?
	public let structuredRecord: DDLRecord?
	public let html: String?

	override public var debugDescription: String {
		if let structuredRecord = structuredRecord {
			return "structuredRecord(\(structuredRecord.debugDescription))"
		}
		else if let html = html {
			return "html(\(html))"
		}

		return super.debugDescription
	}

	override public init(attributes: [String:AnyObject]) {
		func loadStructuredRecord(content: String, _ attributes: [String:AnyObject]) -> DDLRecord? {
			let record = DDLRecord(data: [:], attributes: attributes)
			record.updateCurrentValues(xmlValues: content)

			return record.structure == nil ? nil : record
		}

		func loadHtml(content: String) -> String? {
			return content.asLocalized(NSLocale(localeIdentifier: NSLocale.currentLocaleString))
		}

		let newAttributes: [String:AnyObject]

		if let className = attributes["className"] as? String,
				object = attributes["object"] as? [String:AnyObject],
				modelAttributes = object["modelAttributes"] as? [String:AnyObject],
				modelValues = object["modelValues"] as? String
				where className.hasPrefix("com.liferay") && className.hasSuffix("JournalArticle") {
			newAttributes = attributes.copyAndMerge(modelAttributes).copyAndRemove("object")

			if let structureData = object["DDMStructure"] as? [String:AnyObject] {
				// structured web content
				html = nil

				self.structure = DDMStructure(
					structureData: structureData,
					locale: NSLocale(localeIdentifier: NSLocale.currentLocaleString))

				if let structure = self.structure {
					structuredRecord = DDLRecord(structure: structure)
					if let structuredRecord = self.structuredRecord {
						if modelValues.isXml {
							structuredRecord.updateCurrentValues(xmlValues: modelValues)
						}
						structuredRecord.attributes = modelAttributes
					}
				}
				else {
					structuredRecord = nil
				}
			}
			else {
				// basic web content
				html = loadHtml(modelValues)

				structuredRecord = nil
				structure = nil
			}
		}
		else if let content = attributes["content"] as? String {
			newAttributes = attributes.copyAndRemove("content")

			structuredRecord = loadStructuredRecord(content, newAttributes)
			structure = structuredRecord?.structure

			html = (structuredRecord == nil) ? loadHtml(content) : nil
		}
		else {
			newAttributes = attributes

			structuredRecord = nil
			structure = nil
			html = nil
		}

		super.init(attributes: newAttributes)
	}

}


@IBDesignable public class WebContentListScreenlet: BaseListScreenlet {

	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var folderId: Int64 = 0
	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue


	public var webContentListDelegate: WebContentListScreenletDelegate? {
		return delegate as? WebContentListScreenletDelegate
	}

	public var customEntryQuery: [String:AnyObject]?


	//MARK: BaseListScreenlet

	override public func createPageLoadInteractor(
			page page: Int,
			computeRowCount: Bool)
			-> BaseListPageLoadInteractor {

		let interactor = WebContentListPageLoadInteractor(
			screenlet: self,
			page: page,
			computeRowCount: computeRowCount,
			groupId: self.groupId,
			folderId: self.folderId)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		return interactor
	}

	override public func onLoadPageError(page page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		webContentListDelegate?.screenlet?(self, onWebContentListError: error)
	}

	override public func onLoadPageResult(page page: Int, rows: [AnyObject], rowCount: Int) {
		super.onLoadPageResult(page: page, rows: rows, rowCount: rowCount)

		let webContentEntries = rows as! [WebContentEntry]

		webContentListDelegate?.screenlet?(self, onWebContentListResponseEntries: webContentEntries)
	}

	override public func onSelectedRow(row: AnyObject) {
		webContentListDelegate?.screenlet?(self, onWebContentSelectedEntry: row as! WebContentEntry)
	}

}
