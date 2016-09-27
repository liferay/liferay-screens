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


public class AssetDisplayView_default: BaseScreenletView, AssetDisplayViewModel {

	public override var progressMessages: [String : ProgressMessages] {
		return [
			BaseScreenlet.DefaultAction: [.Working: NoProgressMessage]
		]
	}

	public var asset: Asset?

	public var innerScreenlet: UIView? {
		set {
			if let oldScreenlet = _innerScreenlet {
				oldScreenlet.removeFromSuperview()
				_innerScreenlet = nil
			}

			if let newScreenlet = newValue {
				newScreenlet.frame = CGRect(origin: CGPointZero, size: self.frame.size)
				_innerScreenlet = newValue
				self.addSubview(newScreenlet)
			}
		}
		get {
			return _innerScreenlet
		}
	}

	private var _innerScreenlet: UIView?
}
