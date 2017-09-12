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
#if LIFERAY_SCREENS_FRAMEWORK
    import MBProgressHUD
#endif


open class AndorraTelecomProgressPresenter: MBProgressHUDPresenter {

	override public init() {
		super.init()
        
        
		self.customColor = UIColor(red:0.55, green:0.05, blue:0.34, alpha:1.0)
		self.customOpacity = 0.6
	}
    
    open override func showHUDInView(_ view: UIView, message: String?, forInteractor interactor: Interactor) {
        
        dispatch_main {
            
            if super.instance == nil {
                super.instance = MBProgressHUD.showAdded(to: view, animated:true)
            }
            
            self.configureAndShowHUD(super.instance!,
                                     message: "loading".localized(),
                                     closeMode: .manualClose,
                                     spinnerMode: .indeterminateSpinner)
        }
        
    }
    
    /*
    open override func hideHUDFromView(_ view: UIView?,
                              message: String?,
                              forInteractor interactor: Interactor,
                              withError error: NSError?) {
        
        hideHud()
        
    } */
    
    open override func hideHUDFromView(_ view: UIView?,
                              message: String?,
                              forInteractor interactor: Interactor,
                              withError error: NSError?) {
        
        if message != nil {
            dispatch_main {
                if self.instance == nil {
                    self.instance = MBProgressHUD.showAdded(to: view, animated:true)
                }
                
                self.configureAndShowHUD(self.instance!,
                                         message: "loading".localized(),
                                         closeMode: error == nil ? .autoclose_TouchClosable : .manualClose_TouchClosable,
                                         spinnerMode: .indeterminateSpinner)
            }
        }
        else {
            hideHud()
        }
    }

}
