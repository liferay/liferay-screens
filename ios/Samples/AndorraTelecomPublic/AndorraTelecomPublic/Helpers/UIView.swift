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
import UIKit

extension UIView {

    func animatedChangeBackgroundColor(isHidden: Bool? = nil, color: UIColor = UIColor(white: 1.0, alpha: 0.7)) {
        if let hidden = isHidden {
            self.isHidden = hidden
        }
        UIView.animate(withDuration: 0.8 * 0.4,
                       delay: 0.0,
                       options: UIViewAnimationOptions.curveEaseOut,
                       animations: { self.backgroundColor = color },
                       completion: nil)
    }

    func dismissChangeBackgroundColor(isHidden: Bool? = nil) {
        UIView.animate(withDuration: 0.8,
                       delay: 0.0,
                       usingSpringWithDamping: 100.0,
                       initialSpringVelocity: 0.6,
                       options: [
							.beginFromCurrentState,
							.allowUserInteraction,
							.overrideInheritedOptions,
							.curveEaseOut
						],
                       animations: { self.backgroundColor = UIColor.clear },
                       completion: {(_) in
							if let hidden = isHidden {
								self.isHidden = hidden
							}
					   })
    }

    func animatedChangeBackgroundColor(view: UIView,
									   isUserInteraccionEnable: Bool? = nil,
									   color: UIColor = UIColor(white: 1.0, alpha: 0.7)) {
        if let userInteraccion = isUserInteraccionEnable {
            view.isUserInteractionEnabled = userInteraccion
        }

        UIView.animate(withDuration: 0.8 * 0.4,
                       delay: 0.0,
                       options: UIViewAnimationOptions.curveEaseOut,
                       animations: { view.backgroundColor = color },
                       completion: nil)
    }

    func dismissChangeBackgroundColor(view: UIView, isUserInteraccionEnable: Bool? = nil) {
        UIView.animate(withDuration: 0.8,
                       delay: 0.0,
                       usingSpringWithDamping: 100.0,
                       initialSpringVelocity: 0.6,
                       options: [
							.beginFromCurrentState,
							.allowUserInteraction,
							.overrideInheritedOptions,
							.curveEaseOut
						],
                       animations: { view.backgroundColor = UIColor.clear },
                       completion: {(_) in
							if let userInteraccion = isUserInteraccionEnable {
								view.isUserInteractionEnabled = userInteraccion
							}
					   })
    }
}
