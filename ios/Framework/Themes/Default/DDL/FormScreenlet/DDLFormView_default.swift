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


public class DDLFormView_default: DDLFormTableView {

	override public var progressMessages: [String:ProgressMessages] {
		return [
			DDLFormScreenlet.LoadFormAction : [
				.Working : LocalizedString("default", "ddlform-loading-message", self),
				.Failure : LocalizedString("default", "ddlform-loading-error", self)
			],
			DDLFormScreenlet.LoadRecordAction : [
				.Working : LocalizedString("default", "ddlform-loading-record-message", self),
				.Failure : LocalizedString("default", "ddlform-loading-record-error", self)
			],
			DDLFormScreenlet.SubmitFormAction : [
				.Working : LocalizedString("default", "ddlform-submitting-message", self),
				.Failure : LocalizedString("default", "ddlform-submitting-error", self),
				.Success : LocalizedString("default", "ddlform-submitted", self)
			],
			DDLFormScreenlet.UploadDocumentAction : [
				.Failure : LocalizedString("default", "ddlform-uploading-error", self)
			]
		]
	}


	//MARK: DDLFormTableView 

	override public func onCreated() {
		super.onCreated()

		self.tableView?.alpha = 0.0
	}

	override public func onFinishInteraction(result: AnyObject?, error: NSError?) {
		if self.tableView?.alpha == 0 {
			UIView.animateWithDuration(0.3, animations: {
				self.tableView!.alpha = 1.0
			})
		}
	}

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

}
