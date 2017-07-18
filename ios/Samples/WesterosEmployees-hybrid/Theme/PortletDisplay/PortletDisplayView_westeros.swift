//
//  PortletDisplayView_westeros.swift
//  WesterosEmployees-hybrid
//
//  Created by Francisco Mico on 18/07/2017.
//  Copyright © 2017 Liferay. All rights reserved.
//

import Foundation
import LiferayScreens


open class PortletDisplayView_westeros: PortletDisplayView_default {
    
    override open func createProgressPresenter() -> ProgressPresenter {
        return WesterosProgressPresenter()
    }
    
}
