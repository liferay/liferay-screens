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
import WebKit

class WebDetailViewController: UIViewController {

	// MARK: Outlets

	@IBOutlet var imageView: UIImageView?

	// MARK: Public variables

	open var url: String?

	// MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()

		guard let detailUrl = self.url else {
			return
		}

		let imageUrl = URL(string: detailUrl)
		guard let data = try? Data(contentsOf: imageUrl!) else {
			print("There was an error")
			return
		}

		imageView?.image = UIImage(data: data)
	}
}
