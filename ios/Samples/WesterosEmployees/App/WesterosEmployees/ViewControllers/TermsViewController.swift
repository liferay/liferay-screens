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

class TermsViewController: CardViewController {


	//MARK: Outlets

	@IBOutlet weak var textView: UITextView?


	//MARK: Init methods

	convenience init() {
		self.init(nibName: "TermsViewController", bundle: nil)
	}


	//MARK: UIViewController

	override func viewWillAppear(animated: Bool) {
		if let rtfPath = NSBundle.mainBundle().URLForResource(
				"TermsAndConditions", withExtension: "rtf") {
			do {
				let attributedText = try NSAttributedString(URL: rtfPath,
					options: [NSDocumentTypeDocumentAttribute:NSRTFTextDocumentType],
					documentAttributes: nil)
				textView?.attributedText = attributedText
			} catch {
				print("No RTF content found in Terms and Conditions file")
			}
		} else {
			print("Terms and conditions file not found")
		}
	}
}
