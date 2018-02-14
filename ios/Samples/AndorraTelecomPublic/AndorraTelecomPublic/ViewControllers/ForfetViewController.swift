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

import Foundation
import LiferayScreens
import Hokusai

@objcMembers
class ForfetViewController: UIViewController, WebScreenletDelegate {

    var url: String = ""
    var menuList: String = ""

    @IBOutlet weak var webScreenlet: WebScreenlet!

    override func viewDidLoad() {
        super.viewDidLoad()
        loadWebScreenlet()
        addLogoToNavigationBar()
    }

    func loadWebScreenlet() {
        let webScreenletConfiguration = WebScreenletConfigurationBuilder(url: self.url)
            .set(webType: .other)
            .addCss(localFile: "forfet")
            .addJs(localFile: "forfet")
            .load()

        webScreenlet.configuration = webScreenletConfiguration

        webScreenlet.backgroundColor = UIColor(red:0.83, green:0.02, blue:0.45, alpha:1.0)
        webScreenlet.load()
        webScreenlet.delegate = self
    }

    func addLogoToNavigationBar() {
        let logo = UIImage(named: "Logo") as UIImage?
        let imageView = UIImageView(image:logo)
        imageView.frame.size.width = 100
        imageView.frame.size.height = 32
        imageView.contentMode = UIViewContentMode.scaleAspectFill

        self.navigationItem.titleView = imageView
    }

    func createButtonMenuList() {
        navigationItem.rightBarButtonItem = UIBarButtonItem(
            title: "section".localized(),
            style: .plain,
            target: self,
            action: Colorful.flag ? #selector(createColorfullMenuList) : #selector(createMenuList))
    }

    func createMenuList() {
        let actionSheetController: UIAlertController = UIAlertController(title: "section".localized(), message: "choose".localized(), preferredStyle: .alert)

        let cancelAction: UIAlertAction = UIAlertAction(title: "cancel".localized(), style: .cancel) { _ -> Void in
            actionSheetController.dismiss(animated: true, completion: nil)
        }

        let list = self.menuList.components(separatedBy: "|")

        for item in list {
            var dividedItem = item.components(separatedBy: ",")

            let itemAction: UIAlertAction = UIAlertAction(title: dividedItem[0], style: .default) { _ -> Void in
                self.webScreenlet.inject(injectableScript: JsScript(name: dividedItem[0], js: "gotoId(\"\(dividedItem[1])\")"))
            }
            actionSheetController.addAction(itemAction)
        }

        actionSheetController.addAction(cancelAction)
        self.present(actionSheetController, animated: true, completion: nil)
    }

    func createColorfullMenuList() {
        let hokusai = Hokusai()

        hokusai.colors = HOKColors(
            backGroundColor: UIColor(red:0.82, green:0.02, blue:0.45, alpha:1.0),
            buttonColor: UIColor(red:0.55, green:0.05, blue:0.34, alpha:1.0),
            cancelButtonColor: UIColor(red:0.55, green:0.05, blue:0.34, alpha:1.0),
            fontColor: UIColor.white
        )

        let list = self.menuList.components(separatedBy: "|")

        for item in list {
            var dividedItem = item.components(separatedBy: ",")

            hokusai.addButton(dividedItem[0]) {
                self.webScreenlet.inject(injectableScript: JsScript(name: dividedItem[0], js: "gotoId(\"\(dividedItem[1])\")"))
            }
        }

        hokusai.show()
    }

    func screenlet(_ screenlet: WebScreenlet, onScriptMessageNamespace namespace: String, onScriptMessage message: String) {
        switch namespace {
        case "menu":
            menuList = message
            createButtonMenuList()
        default:
            print("Any event")
        }

    }

}
