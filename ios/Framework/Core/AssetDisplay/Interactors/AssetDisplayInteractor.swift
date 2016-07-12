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


public class AssetDisplayInteractor: ServerReadConnectorInteractor {
	
	public var entryId: Int64?
	
	public var assetEntry: Asset?
	
	init(screenlet: BaseScreenlet, entryId: Int64) {
		self.entryId = entryId
		
		super.init(screenlet: screenlet)
	}
	
	override public func createConnector() -> AssetDisplayLiferayConnector? {
		if let entryId = self.entryId where entryId != 0 {
			return LiferayServerContext.connectorFactory.createAssetDisplayConnector(entryId)
		}
		
		return nil
	}
	
	override public func completedConnector(op: ServerConnector) {
		if let assetEntryConnector = (op as? AssetDisplayLiferayConnector) {
			assetEntry = assetEntryConnector.assetEntry
			entryId = assetEntryConnector.entryId
		}
		else {
			self.assetEntry = nil
			self.entryId = nil
		}
	}
}
