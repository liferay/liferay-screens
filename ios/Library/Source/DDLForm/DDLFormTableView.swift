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

public class DDLFormTableView: DDLFormView, UITableViewDataSource, UITableViewDelegate {

	@IBOutlet var tableView: UITableView?

	internal var elementCellHeights: [DDLElementType:CGFloat] = [:]

	override public func onCreate() {
		super.onCreate()

		registerElementCells()
	}

	override func onChangedRows() {
		super.onChangedRows()
		
		tableView!.reloadData()
	}

	internal func registerElementCells() {
		let currentBundle = NSBundle(forClass: self.dynamicType)

		for elementType in DDLElementType.all() {
			var nibName = "DDLElement\(elementType.toName())TableCell"
			if let themeName = themeName() {
				nibName += "-" + themeName
			}

			if currentBundle.pathForResource(nibName, ofType: "nib") {
				var cellNib = UINib(nibName: nibName, bundle: currentBundle)

				tableView?.registerNib(cellNib, forCellReuseIdentifier: elementType.toRaw())

				registerElementCellHeight(type:elementType, nib:cellNib)
			}
		}
	}

	internal func registerElementCellHeight(#type:DDLElementType, nib:UINib) {
		if let views = nib.instantiateWithOwner(nil, options: nil) {
			if let cellRootView = views.first as? UITableViewCell {
				elementCellHeights[type] = cellRootView.bounds.size.height
			}
			else {
				println("ERROR: Root view in cell \(type.toRaw()) didn't found")
			}
		}
		else {
			println("ERROR: Can't instantiate nib for cell \(type.toRaw())")
			elementCellHeights[type] = 0
		}
	}

	// MARK: UITableViewDataSource

	public func tableView(tableView: UITableView!, numberOfRowsInSection section: Int) -> Int {
		return rows.count
	}

	public func tableView(tableView: UITableView!, cellForRowAtIndexPath indexPath: NSIndexPath!) -> UITableViewCell! {

		let element = rows[indexPath.row]

		let cell = tableView.dequeueReusableCellWithIdentifier(element.type.toRaw()) as? DDLElementTableCell

		if let cellValue = cell {
			cellValue.element = element
		}

		return cell
	}

	public func tableView(tableView: UITableView!, heightForRowAtIndexPath indexPath: NSIndexPath!) -> CGFloat {

		let element = rows[indexPath.row]

		return elementCellHeights[element.type] ?? 0
	}

}