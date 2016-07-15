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
import Foundation


@objc public class WebContent : Asset {

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

	public class func isWebContentClassName(className: String) -> Bool {
		return className.hasPrefix("com.liferay.") && className.hasSuffix(".JournalArticle")
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
			where WebContent.isWebContentClassName(className) {
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
						else {
							print("ERROR: Structured web content's values should always be returned in xml")
							structuredRecord.clearValues()
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

	public required init?(coder aDecoder: NSCoder) {
		self.structure = aDecoder.decodeObjectForKey("webcontent-structure") as? DDMStructure
		self.structuredRecord = aDecoder.decodeObjectForKey("webcontent-structuredRecord") as? DDLRecord
		self.html = aDecoder.decodeObjectForKey("webcontent-html") as? String

		super.init(coder: aDecoder)
	}

	public override func encodeWithCoder(aCoder: NSCoder) {
		if let structure = self.structure {
			aCoder.encodeObject(structure, forKey:"webcontent-structure")
		}
		if let structuredRecord = self.structuredRecord {
			aCoder.encodeObject(structuredRecord, forKey:"webcontent-structuredRecord")
		}
		if let html = self.html {
			aCoder.encodeObject(html, forKey:"webcontent-html")
		}
	}
	
}
