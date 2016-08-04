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


public class BaseFileDisplayScreenlet: BaseScreenlet {

	public static let LoadFile = "LoadFileAction"

	@IBInspectable public var entryId: Int64 = 0

	@IBInspectable public var classPK: Int64 = 0
	@IBInspectable public var className: String = ""

	@IBInspectable public var autoLoad: Bool = true

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue

	public var fileEntry: FileEntry?

	//MARK: BaseScreenlet

	override public func onShow() {
		if autoLoad {
			load()
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		if isActionRunning(name) {
			cancelInteractorsForAction(name)
		}

		switch name {
		case BaseScreenlet.DefaultAction:
			return createLoadAssetInteractor()
		case BaseFileDisplayScreenlet.LoadFile:
			return createLoadFileInteractor()
		default:
			return nil
		}
	}

	//MARK: Public methods

	public func load() -> Bool {
		return performDefaultAction()
	}

	public func loadFile() -> Bool {
		return self.performAction(name: BaseFileDisplayScreenlet.LoadFile)
	}

	//Child classes must override this method
	public func createLoadAssetInteractor() -> Interactor? {
		return nil
	}

	//Child classes must override this method
	public func createLoadFileInteractor() -> Interactor? {
		return nil
	}
}
