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

extension SyncManager {

	func commentsSynchronizer(
			key: String,
			attributes: [String:AnyObject])
			-> Signal -> () {

		if key.hasPrefix("delete-") {
			return deleteSynchronizer(key, attributes: attributes)
		}
		else if key.hasPrefix("update-") {
			return updateSynchronizer(key, attributes: attributes)
		}

		return { _ in }
	}

	func deleteSynchronizer(
			key: String,
			attributes: [String:AnyObject])
			-> Signal -> () {
		return { signal in
			let commentId = (attributes["commentId"] as! NSNumber).longLongValue

			let interactor = CommentDeleteInteractor(commentId: commentId);

			self.prepareInteractorForSync(interactor,
					key: key,
					attributes: attributes,
					signal: signal,
					screenletClassName: "CommentsScreenlet")

			if !interactor.start() {
				self.delegate?.syncManager?(self,
						onItemSyncScreenlet: "CommentsScreenlet",
						failedKey: key,
						attributes: attributes,
						error: NSError.errorWithCause(.NotAvailable))
				signal()
			}
		}
	}

	func updateSynchronizer(
			key: String,
			attributes: [String:AnyObject])
			-> Signal -> () {
		return { signal in
			let groupId = (attributes["groupId"] as! NSNumber).longLongValue
			let className = (attributes["className"] as! String)
			let classPK = (attributes["classPK"] as! NSNumber).longLongValue
			let commentId = (attributes["commentId"] as! NSNumber).longLongValue
			let body = (attributes["body"] as! String)

			let interactor = CommentUpdateInteractor(
					groupId: groupId,
					className: className,
					classPK: classPK,
					commentId: commentId,
					body: body)

			self.prepareInteractorForSync(interactor,
					key: key,
					attributes: attributes,
					signal: signal,
					screenletClassName: "CommentsScreenlet")

			if !interactor.start() {
				self.delegate?.syncManager?(self,
						onItemSyncScreenlet: "CommentsScreenlet",
						failedKey: key,
						attributes: attributes,
						error: NSError.errorWithCause(.NotAvailable))
				signal()
			}
		}
	}


}
