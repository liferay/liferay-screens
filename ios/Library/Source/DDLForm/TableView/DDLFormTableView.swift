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

	@IBOutlet internal var tableView: UITableView?

	internal var firstCellResponder:UIResponder?

	internal var submitButtonHeight:CGFloat = 0.0


	//MARK: DDLFormView

	override public func resignFirstResponder() -> Bool {
		var result:Bool = false

		if let cellValue = firstCellResponder {
			result = cellValue.resignFirstResponder()
			if result {
				firstCellResponder = nil
			}
		}

		return result
	}

	override public func becomeFirstResponder() -> Bool {
		var result = false

		let rowCount = tableView!.numberOfRowsInSection(0)
		var indexPath = NSIndexPath(forRow: 0, inSection: 0)

		while !result && indexPath.row < rowCount {
			if let cell = tableView!.cellForRowAtIndexPath(indexPath) {
				if cell.canBecomeFirstResponder() {
					result = true
					cell.becomeFirstResponder()
				}

			}
			indexPath = NSIndexPath(forRow: indexPath.row.successor(), inSection: indexPath.section)
		}

		return result
	}

	override internal func onCreated() {
		super.onCreated()

		registerElementCells()
	}

	override internal func onChangedRows() {
		super.onChangedRows()

		for element in rows {
			element.resetCurrentHeight()
		}
		
		tableView!.reloadData()
	}

	override internal func showElement(element: DDLElement) {
		if let row = find(rows, element) {
			tableView!.scrollToRowAtIndexPath(
				NSIndexPath(forRow: row, inSection: 0),
				atScrollPosition: .Top, animated: true)
		}
	}

	override internal func changeDocumentUploadStatus(element: DDLElementDocument) {
		if let row = find(rows, element) {
			if let cell = tableView!.cellForRowAtIndexPath(
					NSIndexPath(forRow: row, inSection: 0)) as? DDLElementTableCell {
				cell.changeDocumentUploadStatus(element)
			}
		}
	}


	//MARK: UITableViewDataSource

	public func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		if rows.count == 0 {
			return 0
		}

		return rows.count + (showSubmitButton ? 1 : 0)
	}

	public func tableView(tableView: UITableView,
			cellForRowAtIndexPath indexPath: NSIndexPath)
			-> UITableViewCell {

		var cell:DDLElementTableCell?
		let row = indexPath.row

		if row == rows.count {
			cell = tableView.dequeueReusableCellWithIdentifier("SubmitButton") as?
					DDLElementTableCell

			cell!.formView = self
		}
		else {
			let element = rows[row]
			
			cell = tableView.dequeueReusableCellWithIdentifier(
					element.editorType.toCapitalizedName()) as? DDLElementTableCell

			if cell == nil {
				println("ERROR: Cell XIB is not registerd for type " +
						element.editorType.toCapitalizedName())
			}

			cell!.formView = self
			cell!.tableView = tableView
			cell!.indexPath = indexPath
			cell!.element = element
		}

		return cell!
	}

	public func tableView(tableView: UITableView,
			heightForRowAtIndexPath indexPath: NSIndexPath)
			-> CGFloat {

		let row = indexPath.row

		return (row == rows.count) ? submitButtonHeight : rows[row].currentHeight
	}


	//MARK: Internal methods

	internal func registerElementCells() {
		let currentBundle = NSBundle(forClass: self.dynamicType)

		for elementEditor in DDLElement.Editor.all() {
			var nibName = "DDLElement\(elementEditor.toCapitalizedName())TableCell"
			if let themeNameValue = themeName {
				nibName += "-" + themeNameValue
			}

			if currentBundle.pathForResource(nibName, ofType: "nib") != nil {
				var cellNib = UINib(nibName: nibName, bundle: currentBundle)

				tableView?.registerNib(cellNib,
						forCellReuseIdentifier: elementEditor.toCapitalizedName())

				registerElementEditorHeight(editor:elementEditor, nib:cellNib)
			}
		}

		if showSubmitButton {
			var nibName = "DDLSubmitButtonTableCell"
			if let themeNameValue = themeName {
				nibName += "-" + themeNameValue
			}

			if currentBundle.pathForResource(nibName, ofType: "nib") != nil {
				var cellNib = UINib(nibName: nibName, bundle: currentBundle)

				tableView?.registerNib(cellNib, forCellReuseIdentifier: "SubmitButton")

				let views = cellNib.instantiateWithOwner(nil, options: nil)

				if let cellRootView = views.first as? UITableViewCell {
					submitButtonHeight = cellRootView.bounds.size.height
				}
				else {
					println("ERROR: Root view in submit button didn't found")
				}
			}
			else {
				println("ERROR: Can't register cell for submit button: \(nibName)")
			}
		}
	}

	internal func registerElementEditorHeight(#editor:DDLElement.Editor, nib:UINib) {
		let views = nib.instantiateWithOwner(nil, options: nil)

		if let cellRootView = views.first as? UITableViewCell {
			editor.registerHeight(cellRootView.bounds.size.height)
		}
		else {
			println("ERROR: Root view in cell \(editor.toRaw()) didn't found")
		}
	}

}