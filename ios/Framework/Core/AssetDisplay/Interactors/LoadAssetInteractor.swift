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


public protocol LoadAssetConnector {
	var resultAsset: Asset? { get set }
}

public class LoadAssetInteractor: ServerReadConnectorInteractor {

	public let entryId: Int64

	public let className: String
	public let classPK: Int64

	public var asset: Asset?

	init(screenlet: BaseScreenlet, entryId: Int64, className: String, classPK: Int64) {
		self.entryId = entryId
		self.className = className
		self.classPK = classPK

		super.init(screenlet: screenlet)
	}

	override public func createConnector() -> ServerConnector? {
		if entryId != 0 {
			return LiferayServerContext.connectorFactory.createAssetLoadByEntryIdConnector(entryId)
		} else {
			return LiferayServerContext.connectorFactory.createAssetLoadByClassPKConnector(className, classPK: classPK)
		}
	}

	override public func completedConnector(c: ServerConnector) {
		asset = (c as? LoadAssetConnector)?.resultAsset
	}
}
