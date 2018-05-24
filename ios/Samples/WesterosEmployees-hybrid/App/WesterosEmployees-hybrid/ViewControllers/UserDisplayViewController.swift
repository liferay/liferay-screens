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

open class UserDisplayViewController: UIViewController {

	// MARK: Outlets

    @IBAction func goBack(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

    @IBAction func signOut(_ sender: Any) {
        SessionContext.currentContext?.removeStoredCredentials()
        SessionContext.logout()
        self.dismiss(animated: true, completion: nil)
    }

    @IBOutlet weak var webScreenlet: WebScreenlet!

	// MARK: UIViewController

	open override func viewDidLoad() {
		super.viewDidLoad()
        loadWebScreenlet()
	}

    func loadWebScreenlet() {
        let webScreenletConfiguration = WebScreenletConfigurationBuilder(url: "/web/westeros-hybrid/userprofile").enableCordova().addCss(localFile: "user_profile").addJs(localFile: "user_profile").load()
        webScreenlet.configuration = webScreenletConfiguration
        webScreenlet.load()
    }

}
