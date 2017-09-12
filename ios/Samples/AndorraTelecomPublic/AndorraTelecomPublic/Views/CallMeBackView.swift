//
//  CallMeBackView.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 19/08/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import UIKit
import Alamofire

class CallMeBackView: UIView {
    
    weak var delegate:CallMeBackDelegate?
    
    @IBOutlet var view: UIView!
    
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var phoneLabel: UILabel!
    @IBOutlet weak var phoneTextField: UITextFieldExtended!
    @IBOutlet weak var legalLabel: UILabel!
    @IBOutlet weak var legalSwitch: UISwitch!
    @IBOutlet weak var callMeNowButton: UIButton!
    @IBOutlet weak var orLabel: UILabel!
    @IBOutlet weak var iCallButton: UIButton!
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        Bundle.main.loadNibNamed("CallMeBackView", owner: self, options: nil)
        self.addSubview(view)
        view.frame = self.bounds
        setTextOutlets()
        addTouchUpToLegalLabel()
    }
    
    func addTouchUpToLegalLabel() {
        legalLabel.isUserInteractionEnabled = true
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(legalTouchUpPopOver(tapGestureRecognizer:)))
        legalLabel.addGestureRecognizer(tapGesture)
    }
    
    func legalTouchUpPopOver(tapGestureRecognizer: UITapGestureRecognizer) {
        delegate?.showLegalCoditions(callMeBackView: self)
    }
    
    func legalConditionsChange(isAccepted: Bool) {
        legalSwitch.setOn(isAccepted, animated: true)
    }
    
    @IBAction func callMeNow(_ sender: UIButton) {
        let phoneCount = phoneTextField.text?.characters.count
        if phoneCount != 6{
            delegate?.showAlertLegalNotAccepted(callMeBackView: self, title: "title-legal".localized()!, message: "phone-not-valid".localized()!)
        }
        if legalSwitch.isOn {
            delegate?.showAlertLegalNotAccepted(callMeBackView: self, title: "title-call".localized()!, message: "call".localized()!)
        } else {
            delegate?.showAlertLegalNotAccepted(callMeBackView: self, title: "title-legal".localized()!, message: "legal-no-accept".localized()!)
        }
    }
    
    /*func requestCallMeNow() {
        let url = "https://www.andorratelecom.ad/particulares/inicio?p_p_id=ad_at_portal_callmeback_portlet_CallMeBackPortlet_INSTANCE_callMeBack&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&_ad_at_portal_callmeback_portlet_CallMeBackPortlet_INSTANCE_callMeBack_CMD=sendEmail"
        let headers: HTTPHeaders = [
            "Content-Type": "application/x-www-form-urlencoded",
            "Accept": "application/json",
        ]
        let parameters: Parameters = [
            "_ad_at_portal_callmeback_portlet_CallMeBackPortlet_INSTANCE_callMeBack_body": "343242",
            "ad_at_portal_callmeback_portlet_CallMeBackPortlet_INSTANCE_callMeBack_captchaText": "1042"
        ]
        
        Alamofire.request(url, headers: headers, method: .post).responseJSON{ (response) in
            print(response)
        }
    }*/
    
    @IBAction func iCall(_ sender: UIButton) {
        if let url = URL(string: "tel://100900900") {
            if #available(iOS 10, *) {
                UIApplication.shared.open(url, options: [:], completionHandler: nil)
            } else {
                UIApplication.shared.openURL(url as URL)
            }
        }
    }
    
    func setTextOutlets() {
        title.text = "help?".localized()
        phoneLabel.text = "phone".localized()
        
        let mutableString = NSMutableAttributedString(string: "accept-legal".localized()!, attributes: nil)
        let range = rangeLastTwoWords(words: "accept-legal".localized()!)
        let attributtes =
            [NSForegroundColorAttributeName : UIColor(red:0.83, green:0.02, blue:0.45, alpha:1.0),
            NSUnderlineStyleAttributeName : 1] as [String : Any]
        mutableString.addAttributes(attributtes, range: range)
        
        legalLabel.attributedText = mutableString
        
        callMeNowButton.setTitle("call-me-now".localized(), for: .normal)
        orLabel.text = "or".localized()
        iCallButton.setTitle("i-call".localized(), for: .normal)
    }
    
    func rangeLastTwoWords(words: String) -> NSRange {
        let countWords = words.characters.count
        let arrayWords = words.components(separatedBy: " ")
        let lastPosition = arrayWords.count - 1
        
        let countLastTwoWords = arrayWords[lastPosition].characters.count + arrayWords[lastPosition - 1].characters.count + 1
        
        return NSRange(location: countWords - countLastTwoWords, length: countLastTwoWords)
    }
    
}
