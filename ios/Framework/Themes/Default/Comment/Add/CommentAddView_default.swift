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


public class CommentAddView_default: BaseScreenletView, CommentAddViewModel {

	@IBOutlet weak var addCommentTextField: UITextField?
	@IBOutlet weak var sendCommentButton: UIButton?

	public var body: String {
		get {
			return addCommentTextField?.text ?? ""
		}
		set {
			addCommentTextField?.text = newValue
			updateButton()
		}
	}


	//MARK: Public methods

	public func updateButton() {
		sendCommentButton?.enabled = !(addCommentTextField?.text?.isEmpty ?? true)
	}


	//MARK: BaseScreenletView

	public override func onShow() {
		addCommentTextField?.delegate = self
		sendCommentButton?.replaceAttributedTitle(
			LocalizedString("default", key: "comment-add-send", obj: self),
			forState: .Normal)
	}

	override public func createProgressPresenter() -> ProgressPresenter {
		return DefaultProgressPresenter()
	}


	//MARK: View actions

	@IBAction func editingDidChangeAction() {
		updateButton()
	}


	//MARK: UITextFieldDelegate

	public override func textFieldShouldReturn(textField: UITextField) -> Bool {
		userAction(name: "add-comment", sender: textField)
		return super.textFieldShouldReturn(textField)
	}
}
