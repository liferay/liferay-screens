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

class MapViewController: UIViewController {

    @IBOutlet weak var webScreenlet: WebScreenlet!

    override func viewDidLoad() {
        super.viewDidLoad()
        loadWebScreenlet()
        addLogoToNavegationBar()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    func loadWebScreenlet() {
        let webScreenletConfiguration = WebScreenletConfigurationBuilder(url: LanguageHelper.shared().url(page: .map))
            .set(webType: .other)
            .addCss(localFile: "map")
            .addJs(localFile: "map")
            .load()
        webScreenlet.configuration = webScreenletConfiguration
        webScreenlet.backgroundColor = UIColor(red:0.83, green:0.02, blue:0.45, alpha:1.0)
        webScreenlet.load()
    }

    func addLogoToNavegationBar() {
        let logo = UIImage(named: "Logo") as UIImage?
        let imageView = UIImageView(image:logo)
        imageView.frame.size.width = 100
        imageView.frame.size.height = 32
        imageView.contentMode = UIViewContentMode.scaleAspectFill

        self.navigationItem.titleView = imageView
    }
}
