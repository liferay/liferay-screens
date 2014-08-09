//
//  DDLElementCell.swift
//  Liferay-iOS-Widgets-Swift-Sample
//
//  Created by jmWork on 08/08/14.
//  Copyright (c) 2014 Liferay. All rights reserved.
//

import UIKit

public class DDLElementCheckboxTableCell_default: DDLElementTableCell {

	@IBOutlet var switchView: UISwitch?
	@IBOutlet var label: UILabel?

	override func onChangedElement() {
		if let boolElement = element as? DDLBooleanElement {
			switchView?.on = boolElement.predefinedValue as Bool
			label?.text = boolElement.label
		}
	}



}
