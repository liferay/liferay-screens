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


public class AddBookmarkProgressPresenter: MBProgressHUDPresenter {
    
    let button: UIButton?
    
    let activityIndicator: UIActivityIndicatorView?
    
    public init(button: UIButton?, activityIndicator: UIActivityIndicatorView?) {
        self.button = button
        self.activityIndicator = activityIndicator
        super.init()
    }
    
    public override func showHUDInView(view: UIView, message: String?, forInteractor interactor: Interactor) {
        guard interactor is GetWebTitleInteractor else {
            return super.showHUDInView(view, message: message, forInteractor: interactor)
        }
        
        button?.hidden = true
        activityIndicator?.startAnimating()
    }
    
    public override func hideHUDFromView(view: UIView?, message: String?, forInteractor interactor: Interactor, withError error: NSError?) {
        guard interactor is GetWebTitleInteractor else {
            return super.hideHUDFromView(view, message: message, forInteractor: interactor, withError: error)
        }
        
        activityIndicator?.stopAnimating()
        button?.hidden = false
    }
    
}
