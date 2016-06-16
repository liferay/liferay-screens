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
import LiferayScreens


@objc public class WebContentDisplayView_book: BaseScreenletView, WebContentDisplayViewModel {

	@IBOutlet weak var authorLabel: UILabel!
	@IBOutlet weak var nameLabel: UILabel!

	override public var progressMessages: [String:ProgressMessages] {
		return [
			BaseScreenlet.DefaultAction :
				[.Working : LocalizedString("default", key: "webcontentdisplay-loading-message", obj: self),
				.Failure : LocalizedString("default", key: "webcontentdisplay-loading-error", obj: self)]]
	}


	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}

	//MARK: WebContentDisplayViewModel

	public var htmlContent: String? {
		get {
			return nil
		}
		set {
		}
	}

	public var recordContent: DDLRecord? {
		didSet {
			nameLabel.text = recordContent?["title"]?.description
			authorLabel.text = recordContent?["author"]?.description
		}
	}

}
