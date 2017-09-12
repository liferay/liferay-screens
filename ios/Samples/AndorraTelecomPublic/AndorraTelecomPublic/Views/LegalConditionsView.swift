//
//  LegalConditionsView.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 19/08/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import UIKit
import LiferayScreens

class LegalConditionsView: UIView, WebScreenletDelegate {

    @IBOutlet var view: UIView!
        
    @IBOutlet weak var webScreenlet: WebScreenlet!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        Bundle.main.loadNibNamed("LegalConditionsView", owner: self, options: nil)
        self.addSubview(view)
        view.frame = frame
        view.layer.cornerRadius = 5;
        view.layer.masksToBounds = true;
        loadWebScreenlet()
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        Bundle.main.loadNibNamed("LegalConditionsView", owner: self, options: nil)
        self.addSubview(view)
        view.frame = self.bounds
    }
    
    func loadWebScreenlet() {
        let webScreenletConfiguration = WebScreenletConfiguration
            .Builder(url: LanguageHelper.shared().url(page: .legal))
            .set(webType: .other)
            .addCss(localFile: "legal")
            .addJs(localFile: "legal")
            .load()
        
        webScreenlet.backgroundColor = .clear
        webScreenlet.configuration = webScreenletConfiguration
        webScreenlet.delegate = self
        webScreenlet.load()
    }
    
    func screenlet(_ screenlet: WebScreenlet,
                   onPortletError error: NSError){
        print("Legal Condition PDS Error: \(error)")
    }
    
}
