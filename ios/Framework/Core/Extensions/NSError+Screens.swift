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

public enum ScreenletsErrorCause: Int {

	case AbortedDueToPreconditions = -2
	case InvalidServerResponse = -3

}

public extension NSError {

	public class func errorWithCause(
			cause: ScreenletsErrorCause,
			userInfo: [NSObject : AnyObject]? = nil)
			-> NSError {

		return NSError(
				domain: "LiferayScreenlets",
				code: cause.rawValue,
				userInfo: userInfo)
}

	public class func errorWithCause(
			cause: ScreenletsErrorCause,
			message: String)
			-> NSError {

		let userInfo = [NSLocalizedDescriptionKey: message]

		return NSError(
				domain: "LiferayScreenlets",
				code: cause.rawValue,
				userInfo: userInfo)
	}

}
