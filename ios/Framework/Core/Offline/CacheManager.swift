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
import YapDatabase.YapDatabaseView
import YapDatabase.YapDatabaseFilteredView


public enum CacheStrategyType: String {
	case RemoteOnly = "remote-only"
	case RemoteFirst = "remote-first"
	case CacheOnly = "cache-only"
	case CacheFirst = "cache-first"
}


@objc public class CacheManager: NSObject {

	private static let tableSchemaDatabase = "lr_cache_"

	public let database: YapDatabase
	public let readConnection: YapDatabaseConnection
	public let writeConnection: YapDatabaseConnection

	public init(database: YapDatabase) {
		self.database = database
		readConnection = database.newConnection()
		writeConnection = database.newConnection()

		super.init()

		registerPendingToSyncView(nil)
	}

	public convenience init(name: String) {
		let dbPath = CacheManager.databasePath(name)

		self.init(database: YapDatabase(path: dbPath))
	}

	public convenience init(session: LRSession, userId: Int64) {
		self.init(name: "\(session.serverName!)-\(userId)")
	}

	public func getString(collection collection: String, key: String, result: String? -> ()) {
		var value: AnyObject?
		readConnection.asyncReadWithBlock( { transaction in
				value = transaction.objectForKey(key, inCollection: collection)
			}, completionBlock: {
				result((value as? NSObject)?.description)
			})
	}

	public func getImage(collection collection: String, key: String, result: UIImage? -> ()) {

		var value: AnyObject?

		readConnection.asyncReadWithBlock({ transaction in
				value = transaction.objectForKey(key, inCollection: collection)
			}, completionBlock: {
				if let image = value as? UIImage {
					result(image)
				}
				else if let data = value as? NSData {
					result(UIImage(data: data))
				}
				else {
					result(nil)
			}
		})
	}

	public func getAny(collection collection: String, key: String, result: AnyObject? -> ()) {
		var value: AnyObject?

		readConnection.asyncReadWithBlock ({ transaction in
			value = transaction.objectForKey(key, inCollection: collection)
			}, completionBlock: {
				result(value)
		})
	}

	public func getAnyWithAttributes(
			collection collection: String,
			key: String,
			result: (AnyObject?, [String:AnyObject]?) -> ()) {

		getSomeWithAttributes(
				collection: collection,
				keys: [key]) { objects, attributes in

			result(objects[0], attributes[0])
		}
	}

	public func getSomeWithAttributes(
			collection collection: String,
			keys: [String],
			result: ([AnyObject?], [[String:AnyObject]?]) -> ()) {

		var objects = [AnyObject?]()
		var attributes = [[String:AnyObject]?]()

		readConnection.asyncReadWithBlock ({ transaction in
			let keyCount = keys.count

			objects = [AnyObject?](count: keyCount, repeatedValue: nil)
			attributes = [[String:AnyObject]?](count: keyCount, repeatedValue: nil)

			for (i,k) in keys.enumerate() {
				objects[i] = transaction.objectForKey(k, inCollection: collection)

				let metadata = transaction.metadataForKey(k, inCollection: collection) as? CacheMetadata
				attributes[i] = metadata?.attributes
			}

		}, completionBlock: {
			result(objects, attributes)
		})
	}

	public func getSome(collection collection: String, keys: [String], result: [AnyObject?] -> ()) {
		var values = [AnyObject?]()

		readConnection.asyncReadWithBlock ({ transaction in
			for k in keys {
				values.append(transaction.objectForKey(k,
					inCollection: collection))
			}

		}, completionBlock: {
			result(values)
		})
	}


	public func getMetadata(collection collection: String, key: String, result: CacheMetadata? -> ()) {
		var value: AnyObject?

		readConnection.asyncReadWithBlock ({ transaction in
			value = transaction.metadataForKey(key, inCollection: collection)
		}, completionBlock: {
			result(value as? CacheMetadata)
		})
	}

	public func setClean(
			collection collection: String,
			key: String,
			value: NSCoding,
			attributes: [String:AnyObject],
			onCompletion: (() -> ())? = nil) {

		// The item becomes clean (the opposite of dirty,
		// that is: synchronized): updated 'sent' & 'received' dates

		set(collection: collection,
			keys: [key],
			values: [value],
			synchronized: NSDate(),
			attributes: attributes,
			onCompletion: onCompletion)
	}

	public func setClean(
			collection collection: String,
			keys: [String],
			values: [NSCoding],
			attributes: [String:AnyObject],
			onCompletion: (() -> ())? = nil) {

		set(collection: collection,
			keys: keys,
			values: values,
			synchronized: NSDate(),
			attributes: attributes,
			onCompletion: onCompletion)
	}

	public func setDirty(
			collection collection: String,
			key: String,
			value: NSCoding,
			attributes: [String:AnyObject],
			onCompletion: (() -> ())? = nil) {

		// The item becomes dirty: fresh received date but nil sent date
		set(collection: collection,
			keys: [key],
			values: [value],
			synchronized: nil,
			attributes: attributes,
			onCompletion: onCompletion)
	}

	private func set(
			collection collection: String,
			keys: [String],
			values: [NSCoding],
			synchronized: NSDate?,
			attributes: [String:AnyObject],
			onCompletion: (() -> ())? = nil) {

		assert(keys.count == values.count,
			"Keys and values must have same number of elements")


		writeConnection.asyncReadWriteWithBlock ({ transaction in
			let metadata = CacheMetadata(
				synchronized: synchronized,
				attributes: attributes)

			for (i,k) in keys.enumerate() {
				transaction.setObject(values[i],
					forKey: k,
					inCollection: collection,
					withMetadata: metadata)
			}
		}, completionBlock: {
			onCompletion?()
		})
	}

	public func setClean(
			collection collection: String,
			key: String,
			attributes: [String:AnyObject],
			onCompletion: (() -> ())? = nil) {

		setMetadata(collection: collection,
			key: key,
			synchronized: NSDate(),
			attributes: attributes,
			onCompletion: onCompletion)
	}

	private func setMetadata(
			collection collection: String,
			key: String,
			synchronized: NSDate?,
			attributes: [String:AnyObject],
			onCompletion: (() -> ())? = nil) {

		writeConnection.asyncReadWriteWithBlock ({ transaction in
			if transaction.hasObjectForKey(key, inCollection: collection) {
				let newMetadata = CacheMetadata(
					synchronized: synchronized,
					attributes: attributes)

				transaction.replaceMetadata(newMetadata,
					forKey: key,
					inCollection: collection)
			}
		}, completionBlock: {
			onCompletion?()
		})
	}

	public func remove(
			collection collection: String,
			key: String,
			onCompletion: (() -> ())? = nil) {

		writeConnection.asyncReadWriteWithBlock ({ transaction in
			transaction.removeObjectForKey(key, inCollection: collection)
		}, completionBlock: {
			onCompletion?()
		})
	}

	public func remove(collection collection: String, onCompletion: (() -> ())? = nil) {
		writeConnection.asyncReadWriteWithBlock ({ transaction in
			transaction.removeAllObjectsInCollection(collection)
		}, completionBlock:{
			onCompletion?()
		})
	}

	public func removeAll(onCompletion: (() -> ())? = nil) {
		writeConnection.asyncReadWriteWithBlock ({ transaction in
			transaction.removeAllObjectsInAllCollections()
		}, completionBlock: {
			onCompletion?()
		})
	}

	public func countPendingToSync(result: UInt -> ()) {
		var value: UInt = 0

		pendingToSyncTransaction ({ transaction in
			value = transaction?.numberOfItemsInAllGroups() ?? 0
		}, onCompletion: {
			result(value)
		})
	}

	public func pendingToSync(
			result: (String, String, [String:AnyObject]) -> Bool,
			onCompletion: (() -> ())? = nil) {

		pendingToSyncTransaction ({ transaction in
			let groups = transaction?.allGroups() ?? [String]()
			for group in groups {
				transaction?.enumerateKeysAndMetadataInGroup(group) {
						(collection, key, metadata, index, stop) in

					dispatch_main(true) {
						let cacheMetadata = metadata as! CacheMetadata
						if result(collection, key, cacheMetadata.attributes ?? [:]) {
							stop.memory = false
						}
						else {
							stop.memory = true
						}
					}
				}
			}
		}, onCompletion: {
			onCompletion?()
		})
	}

	//MARK "protected" methods

	public class func databasePath(name: String) -> String {
		let cacheFolderPath = NSSearchPathForDirectoriesInDomains(.CachesDirectory, .UserDomainMask, true)[0]
		let path = (cacheFolderPath as NSString).stringByAppendingPathComponent(tableSchemaDatabase)

		let filename = name.toSafeFilename()
		let dbPath = "\(path)_\(filename)"

		CacheManager.fixWrongDatabaseFilename(filename, path: path)

		return dbPath
	}

	public func registerPendingToSyncView(result: (Bool -> ())?) {
		let grouping = YapDatabaseViewGrouping.withKeyBlock { (_, collection, key) in
			return collection
		}

		let sorting = YapDatabaseViewSorting.withKeyBlock { (_, _, _, key1, _, key2) in
			//TODO sort by added date
			return key1.compare(key2)
		}


		let filtering = YapDatabaseViewFiltering.withMetadataBlock({ (_, _, _, _, metadata) in
			let cacheMetadata = metadata as? CacheMetadata
			return cacheMetadata?.synchronized == nil
		})

		let parentView = YapDatabaseView(grouping: grouping, sorting: sorting)

		database.asyncRegisterExtension(parentView,
			withName: "allEntries",
			connection: writeConnection,
			completionQueue: nil) { success in
				if success {
					let filterView = YapDatabaseFilteredView(
						parentViewName: "allEntries",
						filtering: filtering)

					self.database.asyncRegisterExtension(filterView,
						withName: "pendingToSync",
						connection: self.writeConnection,
						completionQueue: nil) { success in
							result?(success)
					}
				}
				else {
					result?(false)
				}
		}
	}


	//MARK: Private methods

	private func pendingToSyncTransaction(
			result: YapDatabaseViewTransaction? -> (),
			onCompletion: () -> ()) {

		if database.registeredExtension("pendingToSync") != nil {
			readConnection.asyncReadWithBlock ({ transaction in
				result(transaction.ext("pendingToSync") as? YapDatabaseViewTransaction)
			}, completionBlock: {
				onCompletion()
			})
		}
		else {
			registerPendingToSyncView { success in
				if success {
					self.readConnection.asyncReadWithBlock ({ transaction in
						result(transaction.ext("pendingToSync") as? YapDatabaseViewTransaction)
					}, completionBlock: {
						onCompletion()
					})
				}
				else {
					result(nil)
					onCompletion()
				}
			}
		}
	}

	private class func fixWrongDatabaseFilename(filename: String, path: String) {
		// Typo in file name in Screens 1.2
		let rightDbPath = "\(path)_\(filename)"
		let wrongDbPath = "\(path)_\(filename))"

		// Use the right filename but rename wrong name first
		if NSFileManager.defaultManager().fileExistsAtPath(wrongDbPath) {
			do {
				try NSFileManager.defaultManager().moveItemAtPath(wrongDbPath, toPath: rightDbPath)
			}
			catch {
			}
			}
	}

}
