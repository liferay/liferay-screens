//
//  LanguageHelper.swift
//  AndorraTelecomPublic
//
//  Created by Francisco Mico on 07/08/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation

class LanguageHelper {
    
    private var languageApp: String
    
    private static var sharedLanguageHelper: LanguageHelper = {
        let languageHelper = LanguageHelper()
        return languageHelper
    }()
    
    class func shared() -> LanguageHelper {
        return sharedLanguageHelper
    }
    
    init() {
        self.languageApp = NSLocale.current.languageCode!
    }
    
    func change(language: String) {
        let endIndex = language.index(language.startIndex, offsetBy: 2)
        self.languageApp = language.substring(to: endIndex)
    }
    
    func currentLanguage() -> String {
        return languageApp
    }
    
    let listLanguages = ["cat", "esp", "fra", "eng"]
    
    var formattedId: String {
        switch languageApp {
        case "ca":
            return "ca_ES"
        case "es":
            return "es_ES"
        case "fr":
            return "fr_FR"
        default:
            return "en_US"
        }
    }
    
    var threeLettersFormatted: String {
        switch languageApp {
        case "ca":
            return "cat"
        case "es":
            return "esp"
        case "fr":
            return "fra"
        default:
            return "eng"
        }
    }
    
    func url(page: Pages) -> String {
        let url = "https://www.andorratelecom.ad/c/portal/update_language?p_l_id=\(page.plid)&redirect=\(page.pathName)&languageId=\(self.formattedId)"
        print("\nURL: \(url)")
        return url
    }
    
    enum Pages: String {
        case index
        case mobileland29
        case roming
        case paquete69
        case optima
        case legal
        case map
        
        var plid: String {
            switch self {
            case .index:
                return "34684"
            case .mobileland29:
                return "100490"
            case .roming:
                return "100870"
            case .paquete69:
                return "108620"
            case .optima:
                return "106754"
            case .legal:
                return "34691"
            case .map:
                return "33998"
            }
        }
        
        var pathName: String {
            switch self {
            case .index:
                return "/particulars/inici/"
            case .mobileland29:
                return "/particulares/som-telefonia-movil/forfets-mobiland/forfet-mobiland-29/"
            case .roming:
                return "/particulares/som-telefonia-movil/otros-forfets/forfet-roaming-espana/"
            case .paquete69:
                return "/particulares/som-internet-fija/paquetes-internet-fija/paquet-69/"
            case .optima:
                return "/particulares/som-internet-fija/paquetes-internet-fija/optima/"
            case .legal:
                return "/avis-legal/"
            case .map:
                return "/web/comercial/on-som"
            }
        }
    }
    
}

extension String {
    func localized() -> String? {
        if let path = Bundle.main.path(forResource: LanguageHelper.shared().currentLanguage(), ofType: "lproj") {
            if let bundle = Bundle(path: path) {
                return NSLocalizedString(self, tableName: "translations", bundle: bundle, value: "", comment: "")
            }
        }
        
        return nil;
    }
}
