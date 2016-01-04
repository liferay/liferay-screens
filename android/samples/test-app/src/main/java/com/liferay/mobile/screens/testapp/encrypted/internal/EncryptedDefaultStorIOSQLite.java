package com.liferay.mobile.screens.testapp.encrypted.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.liferay.mobile.screens.cache.sql.*;
import com.pushtorefresh.storio.internal.ChangesBus;
import com.pushtorefresh.storio.sqlite.Changes;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.pushtorefresh.storio.internal.Checks.checkNotNull;
import static com.pushtorefresh.storio.internal.Environment.RX_JAVA_IS_IN_THE_CLASS_PATH;
import static com.pushtorefresh.storio.internal.InternalQueries.nullableArrayOfStrings;
import static com.pushtorefresh.storio.internal.InternalQueries.nullableString;
import static java.util.Collections.unmodifiableMap;

/**
 * Default implementation of {@link StorIOSQLite} for {@link android.database.sqlite.SQLiteDatabase}.
 * <p>
 * Thread-safe.
 */
public class EncryptedDefaultStorIOSQLite extends com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite {

	public EncryptedDefaultStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper, @Nullable Map<Class<?>, SQLiteTypeMapping<?>> typesMapping, String password) {
		super(null, typesMapping);
		this.sqLiteOpenHelper = sqLiteOpenHelper;
		internal = new InternalImpl(typesMapping);
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 */
	@NonNull
	@Override
	public Internal internal() {
		return internal;
	}

	/**
	 * Closes underlying {@link SQLiteOpenHelper}.
	 * <p>
	 * All calls to this instance of {@link StorIOSQLite}
	 * after call to this method can produce exceptions
	 * and undefined behavior.
	 */
	@Override
	public void close() throws IOException {
		sqLiteOpenHelper.close();
	}
	@NonNull
	private final SQLiteOpenHelper sqLiteOpenHelper;
	@NonNull
	private final ChangesBus<Changes> changesBus = new ChangesBus<Changes>(RX_JAVA_IS_IN_THE_CLASS_PATH);
	private String password;
	/**
	 * Implementation of {@link Internal}.
	 */
	@NonNull
	private final Internal internal;

	/**
	 * {@inheritDoc}
	 */
	protected class InternalImpl extends Internal {

		protected InternalImpl(@Nullable Map<Class<?>, SQLiteTypeMapping<?>> typesMapping) {
			this.directTypesMapping = typesMapping != null
				? unmodifiableMap(typesMapping)
				: null;
		}

		/**
		 * Gets type mapping for required type.
		 * <p>
		 * This implementation can handle subclasses of types, that registered its type mapping.
		 * For example: You've added type mapping for {@code User.class},
		 * and you have {@code UserFromServiceA.class} which extends {@code User.class},
		 * and you didn't add type mapping for {@code UserFromServiceA.class}
		 * because they have same fields and you just want to have multiple classes.
		 * This implementation will find type mapping of {@code User.class}
		 * and use it as type mapping for {@code UserFromServiceA.class}.
		 *
		 * @return direct or indirect type mapping for passed type, or {@code null}.
		 */
		@SuppressWarnings("unchecked")
		@Nullable
		@Override
		public <T> SQLiteTypeMapping<T> typeMapping(final @NonNull Class<T> type) {
			if (directTypesMapping == null) {
				return null;
			}

			final SQLiteTypeMapping<T> directTypeMapping = (SQLiteTypeMapping<T>) directTypesMapping.get(type);

			if (directTypeMapping != null) {
				// fffast! O(1)
				return directTypeMapping;
			}
			else {
				// If no direct type mapping found — search for indirect type mapping

				// May be value already in cache.
				SQLiteTypeMapping<T> indirectTypeMapping
					= (SQLiteTypeMapping<T>) indirectTypesMappingCache.get(type);

				if (indirectTypeMapping != null) {
					// fffast! O(1)
					return indirectTypeMapping;
				}

				// Okay, we don't have direct type mapping.
				// And we don't have cache for indirect type mapping.
				// Let's find indirect type mapping and cache it!
				Class<?> parentType = type.getSuperclass();

				// Search algorithm:
				// Walk through all parent types of passed type.
				// If parent type has direct mapping -> we found indirect type mapping!
				// If current parent type == Object.class -> there is no indirect type mapping.
				// Complexity:
				// O(n) where n is number of parent types of passed type (pretty fast).

				// Stop search if root parent is Object.class
				while (parentType != Object.class) {
					indirectTypeMapping = (SQLiteTypeMapping<T>) directTypesMapping.get(parentType);

					if (indirectTypeMapping != null) {
						// Store this typeMapping as known to make resolving O(1) for the next time
						indirectTypesMappingCache.put(type, indirectTypeMapping);
						return indirectTypeMapping;
					}

					parentType = parentType.getSuperclass();
				}

				// No indirect type mapping found.
				return null;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@WorkerThread
		@Override
		public void executeSQL(@NonNull RawQuery rawQuery) {
			if (rawQuery.args().isEmpty()) {
				getWritableDatabase()
					.execSQL(rawQuery.query());
			}
			else {
				getWritableDatabase()
					.execSQL(
						rawQuery.query(),
						rawQuery.args().toArray(new String[rawQuery.args().size()])
					);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@WorkerThread
		@NonNull
		@Override
		public Cursor rawQuery(@NonNull RawQuery rawQuery) {
			return getReadableDatabase()
				.rawQuery(
					rawQuery.query(),
					nullableArrayOfStrings(rawQuery.args())
				);
		}

		/**
		 * {@inheritDoc}
		 */
		@WorkerThread
		@NonNull
		@Override
		public Cursor query(@NonNull Query query) {
			return getReadableDatabase().query(
				query.distinct(),
				query.table(),
				nullableArrayOfStrings(query.columns()),
				nullableString(query.where()),
				nullableArrayOfStrings(query.whereArgs()),
				nullableString(query.groupBy()),
				nullableString(query.having()),
				nullableString(query.orderBy()),
				nullableString(query.limit())
			);
		}

		/**
		 * {@inheritDoc}
		 */
		@WorkerThread
		@Override
		public long insert(@NonNull InsertQuery insertQuery, @NonNull ContentValues contentValues) {
			return getWritableDatabase()
				.insertOrThrow(
					insertQuery.table(),
					insertQuery.nullColumnHack(),
					contentValues
				);
		}

		/**
		 * {@inheritDoc}
		 */
		@WorkerThread
		@Override
		public long insertWithOnConflict(@NonNull InsertQuery insertQuery, @NonNull ContentValues contentValues, int conflictAlgorithm) {
			return getWritableDatabase()
				.insertWithOnConflict(
					insertQuery.table(),
					insertQuery.nullColumnHack(),
					contentValues,
					conflictAlgorithm
				);
		}

		/**
		 * {@inheritDoc}
		 */
		@WorkerThread
		@Override
		public int update(@NonNull UpdateQuery updateQuery, @NonNull ContentValues contentValues) {
			return getWritableDatabase()
				.update(
					updateQuery.table(),
					contentValues,
					nullableString(updateQuery.where()),
					nullableArrayOfStrings(updateQuery.whereArgs())
				);
		}

		/**
		 * {@inheritDoc}
		 */
		@WorkerThread
		@Override
		public int delete(@NonNull DeleteQuery deleteQuery) {
			return getWritableDatabase()
				.delete(
					deleteQuery.table(),
					nullableString(deleteQuery.where()),
					nullableArrayOfStrings(deleteQuery.whereArgs())
				);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void notifyAboutChanges(@NonNull Changes changes) {
			checkNotNull(changes, "Changes can not be null");

			// Fast path, no synchronization required
			if (numberOfRunningTransactions.get() == 0) {
				changesBus.onNext(changes);
			}
			else {
				synchronized (lock) {
					pendingChanges.add(changes);
				}

				notifyAboutPendingChangesIfNotInTransaction();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void beginTransaction() {
			getWritableDatabase()
				.beginTransaction();

			numberOfRunningTransactions.incrementAndGet();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setTransactionSuccessful() {
			getWritableDatabase()
				.setTransactionSuccessful();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void endTransaction() {
			getWritableDatabase()
				.endTransaction();

			numberOfRunningTransactions.decrementAndGet();
			notifyAboutPendingChangesIfNotInTransaction();
		}

		private void notifyAboutPendingChangesIfNotInTransaction() {
			final Set<Changes> changesToSend;

			if (numberOfRunningTransactions.get() == 0) {
				synchronized (lock) {
					changesToSend = pendingChanges;
					pendingChanges = new HashSet<Changes>(5);
				}
			}
			else {
				changesToSend = null;
			}

			if (changesToSend != null) {
				for (Changes changes : changesToSend) {
					changesBus.onNext(changes);
				}
			}
		}

		private SQLiteDatabase getReadableDatabase() {
			return sqLiteOpenHelper.getReadableDatabase(password);
		}

		private SQLiteDatabase getWritableDatabase() {
			return sqLiteOpenHelper.getWritableDatabase(password);
		}
		@NonNull
		private final Object lock = new Object();
		// Unmodifiable
		@Nullable
		private final Map<Class<?>, SQLiteTypeMapping<?>> directTypesMapping;
		@NonNull
		private final Map<Class<?>, SQLiteTypeMapping<?>> indirectTypesMappingCache
			= new ConcurrentHashMap<Class<?>, SQLiteTypeMapping<?>>();
		@NonNull
		private AtomicInteger numberOfRunningTransactions = new AtomicInteger(0);
		/**
		 * Guarded by {@link #lock}.
		 */
		@NonNull
		private Set<Changes> pendingChanges = new HashSet<Changes>(5);
	}
}
