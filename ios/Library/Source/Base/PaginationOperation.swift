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


public class PaginationOperation: NSObject, LRCallback {

	internal var onOperationSuccess: ((Int, [[String:AnyObject]], Int) -> [AnyObject])?
	internal var onOperationFailure: ((Int, NSError) -> Void)?

	private let page:Int

	internal init(page:Int) {
		self.page = page
	}


	//MARK: Public methods

	public func onFailure(error: NSError!) {
		onOperationFailure?(page, error)
	}

	public func onSuccess(result: AnyObject!) {
		if let responses = result as? NSArray {
			if let entriesResponse = responses.firstObject as? NSArray {
				if let countResponse = responses.objectAtIndex(1) as? NSNumber {
					onOperationSuccess?(page,
						entriesResponse as [[String:AnyObject]],
						countResponse as Int)

					return
				}
			}
		}

		onFailure(createError(cause: .InvalidServerResponse, userInfo: ["ServerResponse" : result]))
	}

}
