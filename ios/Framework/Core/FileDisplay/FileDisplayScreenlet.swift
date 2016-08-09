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


public class FileDisplayScreenlet: BaseScreenlet {

	public static let LoadFileAction = "LoadFileAction"

	@IBInspectable public var assetEntryId: Int64 = 0

	@IBInspectable public var className: String = ""
	@IBInspectable public var classPK: Int64 = 0

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
		case FileDisplayScreenlet.LoadFileAction:
			return createLoadFileInteractor()
		default:
			return nil
		}
	}

	//MARK: Public methods

	public func load() -> Bool {
		if fileEntry == nil {
			return performDefaultAction()
		}
		else {
			return self.performAction(name: FileDisplayScreenlet.LoadFileAction)
		}
	}

	public func createLoadAssetInteractor() -> Interactor? {
		fatalError("Override createLoadAssetInteractor method")
	}

	public func createLoadFileInteractor() -> Interactor? {
		fatalError("Override createLoadFileInteractor method")
	}
}
