//
//  DDListViewCell_westeros.swift
//  WesterosBank
//
//  Created by jmWork on 04/05/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

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
