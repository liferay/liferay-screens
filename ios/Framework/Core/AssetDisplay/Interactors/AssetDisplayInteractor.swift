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


public protocol AssetDisplayConnector {
	var resultAssetEntry: Asset? { get set }
}

public class AssetDisplayInteractor: ServerReadConnectorInteractor {

	public let entryId: Int64

	public let classPK: Int64
	public let className: String

	public var assetEntry: Asset?

	init(screenlet: BaseScreenlet, entryId: Int64, classPK: Int64, className: String) {
		self.entryId = entryId
		self.classPK = classPK
		self.className = className

		super.init(screenlet: screenlet)
	}

	override public func createConnector() -> ServerConnector? {
		if entryId != 0 {
			return LiferayServerContext.connectorFactory.createAssetDisplayByEntryIdConnector(entryId)
		} else {
			return LiferayServerContext.connectorFactory.createAssetDisplayByClassPKConnector(className, classPK: classPK)
		}
	}

	override public func completedConnector(op: ServerConnector) {
		if let assetEntryConnector = op as? AssetDisplayConnector {
			assetEntry = assetEntryConnector.resultAssetEntry
		}
		else {
			self.assetEntry = nil
		}
	}
}
