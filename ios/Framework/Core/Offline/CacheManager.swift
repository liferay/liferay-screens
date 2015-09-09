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
	case RemoteOnly = "remote-only"
	case RemoteFirst = "remote-first"
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

		registerPendingToSyncView(nil)
	}

	public convenience init(session: LRSession) {
		self.init(name: session.serverName!)
	}


	public func getString(#collection: String, key: String, result: String? -> ()) {
		readConnection.readWithBlock { transaction in
			let value: AnyObject? = transaction.objectForKey(key, inCollection: collection)
			result((value as? NSObject)?.description)
		}
	}

	public func getImage(#collection: String, key: String, result: UIImage? -> ()) {
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

	public func getAny(#collection: String, key: String, result: AnyObject? -> ()) {
		readConnection.readWithBlock { transaction in
			result(transaction.objectForKey(key, inCollection: collection))
		}
	}

	public func getSome(#collection: String, keys: [String], result: [AnyObject?] -> ()) {
		readConnection.readWithBlock { transaction in
			var values = [AnyObject?]()

			for k in keys {
				values.append(transaction.objectForKey(k,
					inCollection: collection))
			}

			result(values)
		}
	}


	public func getMetadata(#collection: String, key: String, result: CacheMetadata? -> ()) {
		readConnection.readWithBlock { transaction in
			let value: AnyObject? = transaction.metadataForKey(key, inCollection: collection)

			println("getMetadata \(collection):\(key) -> synchronized: \((value as? CacheMetadata)?.synchronized)")

			result(value as? CacheMetadata)
		}
	}

	public func setClean(
			#collection: String,
			key: String,
			value: NSCoding,
			attributes: [String:AnyObject]) {

		// The item becomes clean (the opposite of dirty,
		// that is: synchronized): updated 'sent' & 'received' dates

		println("==== set Clean")

		set(collection: collection,
			key: key,
			value: value,
			synchronized: NSDate(),
			attributes: attributes)
	}

	public func setDirty(
			#collection: String,
			key: String,
			value: NSCoding,
			attributes: [String:AnyObject]) {

		// The item becomes dirty: fresh received date but nil sent date
		println("==== set Dirty")

		set(collection: collection,
			key: key,
			value: value,
			synchronized: nil,
			attributes: attributes)
	}

	private func set(
			#collection: String,
			key: String,
			value: NSCoding,
			synchronized: NSDate?,
			attributes: [String:AnyObject]) {

		writeConnection.readWriteWithBlock { transaction in
			let metadata = CacheMetadata(
				synchronized: synchronized,
				attributes: attributes)

			transaction.setObject(value,
				forKey: key,
				inCollection: collection,
				withMetadata: metadata)

			println("set \(collection):\(key) -> synchronized: \(synchronized)")
		}
	}

	public func setClean(
			#collection: String,
			key: String,
			attributes: [String:AnyObject]) {

		println("==== set Clean")

		setMetadata(collection: collection,
			key: key,
			synchronized: NSDate(),
			attributes: attributes)
	}

	private func setMetadata(
			#collection: String,
			key: String,
			synchronized: NSDate?,
			attributes: [String:AnyObject]) {

		writeConnection.readWriteWithBlock { transaction in
			if transaction.hasObjectForKey(key, inCollection: collection) {
				let newMetadata = CacheMetadata(
					synchronized: synchronized,
					attributes: attributes)

				transaction.replaceMetadata(newMetadata,
					forKey: key,
					inCollection: collection)

				println("setMetadata \(collection):\(key) -> synchronized=\(newMetadata.synchronized)")
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

	public func countPendingToSync(result: UInt -> ()) {
		pendingToSyncTransaction { transaction in
			dispatch_main(true) {
				result(transaction?.numberOfItemsInAllGroups() ?? 0)
			}
		}
	}

	public func pendingToSync(result: (String, String, [String:AnyObject]) -> Bool) {
		pendingToSyncTransaction { transaction in
			let groups = (transaction?.allGroups() as? [String]) ?? [String]()
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
		}
	}


	//MARK: Private methods

	private func pendingToSyncTransaction(result: YapDatabaseViewTransaction? -> ()) {
		if database.registeredExtension("pendingToSync") != nil {
			readConnection.readWithBlock { transaction in
				result(transaction.ext("pendingToSync") as? YapDatabaseViewTransaction)
			}
		}
		else {
			registerPendingToSyncView { success in
				if success {
					self.readConnection.readWithBlock { transaction in
						result(transaction.ext("pendingToSync") as? YapDatabaseViewTransaction)
					}
				}
				else {
					result(nil)
				}
			}
		}
	}

	private func registerPendingToSyncView(result: (Bool -> ())?) {
		let grouping = YapDatabaseViewGrouping.withKeyBlock { (collection, key) in
			return collection
		}

		let sorting = YapDatabaseViewSorting.withKeyBlock { (_, _, key1, _, key2) in
			//TODO sort by added date
			return key1.compare(key2)
		}

		let filtering = YapDatabaseViewFiltering.withMetadataBlock({ (_,_,_, metadata: AnyObject!) in
			let cacheMetadata = metadata as? CacheMetadata
			return cacheMetadata?.synchronized == nil
		})

		let parentView = YapDatabaseView(grouping: grouping, sorting: sorting)

		database.asyncRegisterExtension(parentView,
			withName: "allEntries",
			connection: writeConnection,
			completionQueue: nil) { success in
				if success {
					let filterView = YapDatabaseFilteredView(parentViewName: "allEntries", filtering: filtering)

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

}
