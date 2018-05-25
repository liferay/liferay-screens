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

class LegalConditionsView: UIView, WebScreenletDelegate {

	// MARK: Outlets

    @IBOutlet var view: UIView!

	@IBOutlet weak var webScreenlet: WebScreenlet? {
		didSet {
			let webScreenletConfiguration = WebScreenletConfigurationBuilder(url: LanguageHelper.shared().url(page: .legal))
				.set(webType: .other)
				.addCss(localFile: "legal")
				.addJs(localFile: "legal")
				.load()

			webScreenlet?.backgroundColor = .clear
			webScreenlet?.configuration = webScreenletConfiguration
			webScreenlet?.delegate = self
		}
	}

	// MARK: Initializers

    override init(frame: CGRect) {
        super.init(frame: frame)

        Bundle.main.loadNibNamed("LegalConditionsView", owner: self, options: nil)
        addSubview(view)
        view.frame = frame
        view.layer.cornerRadius = 5
        view.layer.masksToBounds = true

		webScreenlet?.load()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)

        Bundle.main.loadNibNamed("LegalConditionsView", owner: self, options: nil)
        addSubview(view)
        view.frame = self.bounds
    }

	// MARK: WebScreenletDelegate

	func screenlet(_ screenlet: WebScreenlet, onError error: NSError) {
		print("WebScreenlet error (LegalConditionsView): \(error.debugDescription)")
	}
}
