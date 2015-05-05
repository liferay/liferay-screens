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


@objc public protocol SignUpViewModel {

	var emailAddress: String? { get set }
	var screenName: String? { get set }
	var password: String? { get set }
	var firstName: String? { get set }
	var middleName: String? { get set }
	var lastName: String? { get set }
	var jobTitle: String? { get set }

	var editCurrentUser: Bool { get set }

}
