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


public class AssetDisplayLiferayConnector: ServerConnector {
	
	public let entryId: Int64
	
	public var assetEntry: Asset?
	
	public init(entryId: Int64) {
		self.entryId = entryId
		
		super.init()
	}
	
	
	//MARK: ServerConnector
	
	override public func doRun(session session: LRSession) {
		assetEntry = nil
		
		var result: Asset?
		
		if entryId ?? 0 != 0 {
			result = doGetAssetEntryWithEntryId(entryId, session: session)
		}
		
		if let result = result where lastError == nil {
			assetEntry = result
		} else {
			lastError = NSError.errorWithCause(.InvalidServerResponse)
		}
	}
	
	override public func validateData() -> ValidationError? {
		let error = super.validateData()
		
		if error == nil {
			if entryId == 0 {
				return ValidationError("assetdisplay-screenlet", "undefined-entryid")
			}
		}
		
		return error
	}
	
	internal func doGetAssetEntryWithEntryId(
		entryId: Int64,
		session: LRSession) -> Asset? {
		fatalError("doGetAssetEntryWithEntryId method must be overwritten")
	}
}

public class Liferay62AssetDisplayConnector: AssetDisplayLiferayConnector {
	
	override internal func doGetAssetEntryWithEntryId(
		entryId: Int64,
		session: LRSession) -> Asset? {
		let service = LRAssetEntryService_v7(session: session)
		
		do {
			let result = try service.getEntryWithEntryId(entryId)
			
			let assetEntry = Asset(attributes: result as! [String:AnyObject])
			
			lastError = nil
			
			return assetEntry
		}
		catch let error as NSError {
			lastError = error
		}
		
		return nil
	}
}

public class Liferay70AssetDisplayConnector: AssetDisplayLiferayConnector {
	
	override internal func doGetAssetEntryWithEntryId(
		entryId: Int64,
		session: LRSession) -> Asset? {
		let service = LRAssetEntryService_v7(session: session)
		
		do {
			let result = try service.getEntryWithEntryId(entryId)
			
			let assetEntry = Asset(attributes: result as! [String:AnyObject])
			
			lastError = nil
			
			return assetEntry
		}
		catch let error as NSError {
			lastError = error
		}
		
		return nil
	}
}
