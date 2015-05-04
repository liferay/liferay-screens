//
//  DDListViewCell_westeros.swift
//  WesterosBank
//
//  Created by jmWork on 04/05/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens


public class DDLListViewCell_westeros: UITableViewCell {

	@IBOutlet weak var statusIcon: UIImageView!
	@IBOutlet weak var issueLabel: UILabel!
	@IBOutlet weak var dateLabel: UILabel!

	private let dateFormatter = NSDateFormatter()

	public var record: DDLRecord? {
		didSet {
			if let titleField = record?.fieldBy(name: "Title") {
				issueLabel.text = titleField.currentValueAsString
			}

			if let created = record?.attributes["createDate"] as? NSNumber {
				let date = NSDate(timeIntervalSince1970: created.doubleValue/1000)
				dateLabel.text = "Created on \(dateFormatter.stringFromDate(date))"
			}

			let icons = ["DONE", "FREEZE", "OPEN", "REJECT", "WAITING"]
			let currentIcon = icons[count(issueLabel.text ?? "") % 5]

			statusIcon.image = imageInAnyBundle(
					name: "status_\(currentIcon)",
					currentClass: self.dynamicType,
					currentTheme: "westeros")

		}
	}

	public override func awakeFromNib() {
		dateFormatter.dateStyle = .LongStyle
		dateFormatter.timeStyle = .NoStyle
		dateFormatter.locale = NSLocale.currentLocale()
	}

}
