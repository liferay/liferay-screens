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


@objc public class ReadCache: NSObject {

	private let tableSchemaDatabase = "lr_read_cache_"

	private var database: YapDatabase
	private var readConnection: YapDatabaseConnection
	private var writeConnection: YapDatabaseConnection


	public init(name: String) {
		let cacheFolderPath = NSSearchPathForDirectoriesInDomains(.CachesDirectory, .UserDomainMask, true)[0] as! String
		let path = cacheFolderPath.stringByAppendingPathComponent(tableSchemaDatabase)
		let dbPath = "\(path)_\(name))"

		database = YapDatabase(path: dbPath)
		readConnection = database.newConnection()
		writeConnection = database.newConnection()

		super.init()
	}

	public func get(#collection: String, key: String, result: String -> Void) {
		readConnection.readWithBlock { transaction -> Void in
			if let value = transaction.objectForKey(key, inCollection: collection) as? NSObject {
				result(value.description)
			}
		};
	}

	public func set(#collection: String, key: String, value: String) {
		writeConnection.readWriteWithBlock { transaction -> Void in
			transaction.setObject(value, forKey: key, inCollection: collection)
		}
	}

	public func remove(#collection: String, key: String) {
		writeConnection.readWriteWithBlock { transaction -> Void in
			transaction.removeObjectForKey(key, inCollection: collection)
		}
	}

	public func remove(#collection: String) {
		writeConnection.readWriteWithBlock { transaction -> Void in
			transaction.removeAllObjectsInCollection(collection)
		}
	}

	public func removeAll() {
		writeConnection.readWriteWithBlock { transaction -> Void in
			transaction.removeAllObjectsInAllCollections()
		}
	}

}
