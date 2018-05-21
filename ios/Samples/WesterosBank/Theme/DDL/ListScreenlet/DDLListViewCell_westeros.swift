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
import MGSwipeTableCell


open class DDLListViewCell_westeros: MGSwipeTableCell {

	@IBOutlet weak var statusIcon: UIImageView!
	@IBOutlet weak var issueLabel: UILabel!
	@IBOutlet weak var dateLabel: UILabel!

	fileprivate let dateFormatter = DateFormatter()

	open var record: DDLRecord? {
		didSet {
			if let titleField = record?.fieldBy(name: "Title") {
				issueLabel.text = titleField.currentValueAsString
			}

			if let created = record?.attributes["createDate"] as? NSNumber {
				let date = Date(timeIntervalSince1970: created.doubleValue/1000)
				dateLabel.text = "Created on \(dateFormatter.string(from: date))"
			}

			let icons = ["DONE", "FREEZE", "OPEN", "REJECT", "WAITING"]
			let currentIcon = icons[(issueLabel.text ?? "").count % 5]

			statusIcon.image = Bundle.imageInBundles(
					name: "status_\(currentIcon)",
					currentClass: type(of: self))
		}
	}

	open override func awakeFromNib() {
		dateFormatter.dateStyle = .long
		dateFormatter.timeStyle = .none
		dateFormatter.locale = Locale.current
	}

}
