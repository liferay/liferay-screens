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

	public let assetEntryId: Int64?

	public let className: String?
	public let classPK: Int64?

	public var asset: Asset?

	public convenience init(screenlet: BaseScreenlet, assetEntryId: Int64) {
		self.init(screenlet: screenlet,
				assetEntryId: assetEntryId,
				className: nil,
				classPK: nil)
	}

	public convenience init(screenlet: BaseScreenlet, className: String, classPK: Int64) {
		self.init(screenlet: screenlet,
		          assetEntryId: nil,
		          className: className,
		          classPK: classPK)
	}

	private init(screenlet: BaseScreenlet, assetEntryId: Int64?, className: String?, classPK: Int64?) {
		self.assetEntryId = assetEntryId
		self.className = className
		self.classPK = classPK

		super.init(screenlet: screenlet)
	}

	override public func createConnector() -> ServerConnector? {
		if let assetEntryId = self.assetEntryId {
			return LiferayServerContext.connectorFactory.createAssetLoadByEntryIdConnector(assetEntryId)
		}
		else if let className = self.className, classPK = self.classPK {
			return LiferayServerContext.connectorFactory.createAssetLoadByClassPKConnector(className, classPK: classPK)
		}

		return nil
	}

	override public func completedConnector(c: ServerConnector) {
		asset = (c as? LoadAssetConnector)?.resultAsset
	}
}
