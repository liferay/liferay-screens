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
import YapDatabase


public enum CacheStrategyType: String {
	case OnlineOnly = "online-only"
	case OnlineFirst = "online-first"
	case CacheOnly = "cache-only"
	case CacheFirst = "cache-first"
}


@objc public class CacheManager: NSObject {

	private let tableSchemaDatabase = "lr_cache_"

	private var database: YapDatabase
	private var readConnection: YapDatabaseConnection
	private var writeConnection: YapDatabaseConnection


	public init(name: String) {
		let cacheFolderPath = NSSearchPathForDirectoriesInDomains(.CachesDirectory, .UserDomainMask, true)[0] as! String
		let path = cacheFolderPath.stringByAppendingPathComponent(tableSchemaDatabase)
		let dbPath = "\(path)_\(name.toSafeFilename()))"

		database = YapDatabase(path: dbPath)
		readConnection = database.newConnection()
		writeConnection = database.newConnection()

		super.init()
	}

	public convenience init(session: LRSession) {
		self.init(name: session.serverName!)
	}


	public func getString(#collection: String, key: String, result: String? -> Void) {
		readConnection.readWithBlock { transaction in
			let value: AnyObject? = transaction.objectForKey(key, inCollection: collection)
			result((value as? NSObject)?.description)
		}
	}

	public func getImage(#collection: String, key: String, result: UIImage? -> Void) {
		readConnection.readWithBlock { transaction in
			let value: AnyObject? = transaction.objectForKey(key, inCollection: collection)

			if let image = value as? UIImage {
				result(image)
			}
			else if let data = value as? NSData {
				result(UIImage(data: data))
			}
			else {
				result(nil)
			}
		}
	}

	public func getAny(#collection: String, key: String, result: AnyObject? -> Void) {
		readConnection.readWithBlock { transaction in
			result(transaction.objectForKey(key, inCollection: collection))
		}
	}

	public func getMetadata(#collection: String, key: String, result: CacheMetadata? -> Void) {
		readConnection.readWithBlock { transaction in
			let value: AnyObject? = transaction.metadataForKey(key, inCollection: collection)

			println("getMetadata \(collection):\(key) -> recevied: \((value as? CacheMetadata)?.received) sent: \((value as? CacheMetadata)?.sent)")

			result(value as? CacheMetadata)
		}
	}

	public func set(#collection: String, key: String, value: NSCoding, dateReceived: NSDate?, dateSent: NSDate?) {
		writeConnection.readWriteWithBlock { transaction in
			if (dateReceived == nil || dateSent == nil) {
				// update: get metadata & set
				let currentMetadata = transaction.metadataForKey(key,
					inCollection: collection) as? CacheMetadata

				let newMetadata = CacheMetadata(
					received: dateReceived ?? currentMetadata?.received,
					sent: dateSent ?? currentMetadata?.sent)

				transaction.setObject(value,
					forKey: key,
					inCollection: collection,
					withMetadata: newMetadata)

				println("set-update \(collection):\(key) -> recevied: \(newMetadata.received) sent: \(newMetadata.sent)")
			}
			else {
				// add or overwrite
				let metadata = CacheMetadata(received: dateReceived, sent: dateSent)
				transaction.setObject(value,
					forKey: key,
					inCollection: collection,
					withMetadata: metadata)

				println("set-new \(collection):\(key) -> recevied: \(dateReceived) sent: \(dateSent)")
			}
		}
	}

	public func updateMetadata(
			#collection: String,
			key: String,
			dateReceived: NSDate?,
			dateSent: NSDate?) {

		writeConnection.readWriteWithBlock { transaction in
			if transaction.hasObjectForKey(key, inCollection: collection) {
				// get old current metadata
				let currentMetadata = transaction.metadataForKey(key,
					inCollection: collection) as? CacheMetadata

				let newMetadata = CacheMetadata(
					received: dateReceived ?? currentMetadata?.received,
					sent: dateSent ?? currentMetadata?.sent)

				transaction.replaceMetadata(newMetadata, forKey: key, inCollection: collection)

				println("updateMetadata \(collection):\(key) -> from: r=\(currentMetadata?.received)-s=\(currentMetadata?.sent) to r=\(newMetadata.received)-s=\(newMetadata.sent)")
			}
		}
	}

	public func remove(#collection: String, key: String) {
		writeConnection.readWriteWithBlock { transaction in
			transaction.removeObjectForKey(key, inCollection: collection)
		}
	}

	public func remove(#collection: String) {
		writeConnection.readWriteWithBlock { transaction in
			transaction.removeAllObjectsInCollection(collection)
		}
	}

	public func removeAll() {
		writeConnection.readWriteWithBlock { transaction in
			transaction.removeAllObjectsInAllCollections()
		}
	}

}
