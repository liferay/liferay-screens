//
//  ForfetViewController.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 08/08/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation
import LiferayScreens
import Hokusai

class ForfetViewController: UIViewController, WebScreenletDelegate{

    var url: String = ""
    var menuList: String = ""
    
    @IBOutlet weak var webScreenlet: WebScreenlet!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadWebScreenlet()
        addLogoToNavegationBar()
    }
    
    func loadWebScreenlet() {
        let webScreenletConfiguration = WebScreenletConfiguration
            .Builder(url: self.url)
            .set(webType: .other)
            .addCss(localFile: "forfet")
            .addJs(localFile: "forfet")
            .load()
        
        webScreenlet.configuration = webScreenletConfiguration
        
        webScreenlet.backgroundColor = UIColor(red:0.83, green:0.02, blue:0.45, alpha:1.0)
        webScreenlet.load()
        webScreenlet.delegate = self
    }
    
    func addLogoToNavegationBar() {
        let logo = UIImage(named: "Logo") as UIImage?
        let imageView = UIImageView(image:logo)
        imageView.frame.size.width = 100;
        imageView.frame.size.height = 32;
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
        
    
        let cancelAction: UIAlertAction = UIAlertAction(title: "cancel".localized(), style: .cancel) { action -> Void in
            actionSheetController.dismiss(animated: true, completion: nil)
        }
        
        let fullList = self.menuList.components(separatedBy: "|")
        
        for i in 0...fullList.count - 1 {
            var item = fullList[i].components(separatedBy: ",")
        
            let itemAction: UIAlertAction = UIAlertAction(title: item[0], style: .default) { action -> Void in
                self.webScreenlet.inject(injectableScript: JsScript(name: item[0], js: "gotoId(\"\(item[1])\")"))
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
        
        let fullList = self.menuList.components(separatedBy: "|")
        
        for i in 0...fullList.count - 1 {
            var item = fullList[i].components(separatedBy: ",")
            
            hokusai.addButton(item[0]) {
                self.webScreenlet.inject(injectableScript: JsScript(name: item[0], js: "gotoId(\"\(item[1])\")"))
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
    
    func onWebLoad(_ screenlet: WebScreenlet, url: String) {
        webScreenlet.inject(injectableScript: JsScript(name: "Nombre", js: "ahora()"))
    }
    
}
