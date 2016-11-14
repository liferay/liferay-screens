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

#if LIFERAY_SCREENS_FRAMEWORK
	import DTPickerPresenter
#endif


public class DDLFieldSelectTableCell_default: DDLBaseFieldTextboxTableCell_default,
		UITableViewDataSource, UITableViewDelegate {
		
	let rowHeight: CGFloat = 50

	public var cellBackgroundColor: UIColor = DefaultThemeBasicBlue

	public var options: [DDMFieldStringWithOptions.Option] = []

	public var selectedOptions: [DDMFieldStringWithOptions.Option] {
		return field?.currentValue as? [DDMFieldStringWithOptions.Option] ?? []
	}


	//MARK: UITableViewCell

	public override func  awakeFromNib() {
		super.awakeFromNib()

		defaultTextField?.onRightButtonClick = { [weak self] in
			self?.textField?.becomeFirstResponder()
		}
	}


	//MARK: DDLBaseFieldTextboxTableCell

	override public func onChangedField() {
		super.onChangedField()

		if let stringField = field as? DDMFieldStringWithOptions {
			textField?.text = stringField.currentValueAsLabel

			setFieldPresenter(stringField)
		}
	}


	//MARK: Private methods

	private func setFieldPresenter(field:DDMFieldStringWithOptions) {

		options = field.options

		//Fill the textfield with the selected options (If we are editing an existing record)
		textField?.text = selectedOptions.map { $0.label }
					.sort()
					.reduce("", combine:{ $0 + " " + $1})
					.trim()

		let tableView = createPresenterTableView(multipleSelection: field.multiple)

		let optionsPresenter = DTViewPresenter(view: tableView)

		optionsPresenter.presenterView.backgroundColor = UIColor.whiteColor()
		optionsPresenter.presenterView.layer.borderColor = UIColor.lightGrayColor().CGColor
		optionsPresenter.presenterView.layer.borderWidth = 1.5

		textField?.dt_setPresenter(optionsPresenter)
	}

	public func createPresenterTableView(multipleSelection multiple: Bool) -> UITableView {

		let tableView = UITableView(
			frame: CGRect(x: 0, y: 0, width: 0,
				height: min(300, rowHeight * CGFloat(options.count))))

		tableView.dataSource = self
		tableView.delegate = self
		tableView.rowHeight = rowHeight
		tableView.registerClass(UITableViewCell.self, forCellReuseIdentifier: "cell")
		tableView.allowsMultipleSelection = multiple

		return tableView
	}

	public func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return options.count
	}


	//MARK: UITableViewDataSource

	public func tableView(
			tableView: UITableView,
			cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {

		let cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath)

		let backgroundView = UIView()
		backgroundView.backgroundColor = cellBackgroundColor

		cell.selectedBackgroundView = backgroundView

		cell.textLabel?.text = options[indexPath.row].label
		cell.textLabel?.textAlignment = .Center
		cell.textLabel?.highlightedTextColor = .whiteColor()

		//Select cells from current selected options
		if selectedOptions.contains(options[indexPath.row]) {
			tableView.selectRowAtIndexPath(indexPath, animated: false, scrollPosition: .Middle)
		}

		return cell
	}


	//MARK: UITableViewDelegate

	public func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
		let selectedIndexPaths = tableView.indexPathsForSelectedRows
		updateFieldCurrentValue(selectedIndexPaths)
	}

	public func tableView(tableView: UITableView, didDeselectRowAtIndexPath indexPath: NSIndexPath) {
		let selectedIndexPaths = tableView.indexPathsForSelectedRows
		updateFieldCurrentValue(selectedIndexPaths)
	}

	public func updateFieldCurrentValue(selectedIndexPaths: [NSIndexPath]?) {
		let currentValue = selectedIndexPaths?
			.map { self.options[$0.row].label }
			.sort()
			.reduce("", combine: { $0 + "," + $1 }) ?? ""

		field?.currentValue = currentValue
		textField?.text = currentValue
				.stringByReplacingOccurrencesOfString(",", withString: " ")
				.trim()
	}

}
