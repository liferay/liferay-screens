//
//  CallMeBackDelegate.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 19/08/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation

protocol CallMeBackDelegate: class {
    
    func showAlertLegalNotAccepted(callMeBackView: CallMeBackView, title: String, message: String)
    
    func showLegalCoditions(callMeBackView: CallMeBackView)
    
}
