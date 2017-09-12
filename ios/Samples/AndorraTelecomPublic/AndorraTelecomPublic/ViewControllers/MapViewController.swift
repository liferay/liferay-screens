//
//  MapViewController.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 19/08/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

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
        let webScreenletConfiguration = WebScreenletConfiguration
            .Builder(url: LanguageHelper.shared().url(page: .map))
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
        imageView.frame.size.width = 100;
        imageView.frame.size.height = 32;
        imageView.contentMode = UIViewContentMode.scaleAspectFill
        
        self.navigationItem.titleView = imageView
    }

}
