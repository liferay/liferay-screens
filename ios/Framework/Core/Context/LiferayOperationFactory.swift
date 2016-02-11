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


@objc(LiferayOperationFactory)
public protocol LiferayOperationFactory {

	func createDDLFormLoadOperation(structureId: Int64) -> LiferayDDLFormLoadOperation

}


@objc(Liferay62OperationFactory)
public class Liferay62OperationFactory: NSObject, LiferayOperationFactory {

	public func createDDLFormLoadOperation(structureId: Int64) -> LiferayDDLFormLoadOperation {
		return Liferay62DDLFormLoadOperation(
			structureId: NSNumber(longLong: structureId))
	}

}


@objc(Liferay70OperationFactory)
public class Liferay70OperationFactory: NSObject, LiferayOperationFactory {

	public func createDDLFormLoadOperation(structureId: Int64) -> LiferayDDLFormLoadOperation {
		return Liferay70DDLFormLoadOperation(
			structureId: NSNumber(longLong: structureId))
	}

}