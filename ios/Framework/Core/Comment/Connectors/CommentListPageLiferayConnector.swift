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

public class CommentListPageLiferayConnector: PaginationLiferayConnector {

	public let groupId: Int64
	public let className: String
	public let classPK: Int64

	public init(groupId: Int64, className: String, classPK: Int64, startRow: Int, endRow: Int, computeRowCount: Bool) {
		self.groupId = groupId
		self.className = className
		self.classPK = classPK

		super.init(startRow: startRow, endRow: endRow, computeRowCount: true)
	}


	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if groupId <= 0 {
				return ValidationError("comment-list-screenlet", "undefined-groupId")
			}

			if classPK <= 0 {
				return ValidationError("comment-list-screenlet", "undefined-classPK")
			}

			if className.isEmpty {
				return ValidationError("comment-list-screenlet", "undefined-className")
			}
		}

		return error
	}
}
