package ua.meugen.android.syncadapter.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import ua.meugen.android.syncadapter.db.SyncDb
import ua.meugen.android.syncadapter.db.SyncItemContract

/**
 * Created by meugen on 02.12.2017.
 */
class SyncProvider: ContentProvider() {

    companion object {
        val AUTHORITY: String get() = "ua.meugen.android.syncadapter"

        val ITEMS_PATH = "items"
        val ITEMS_URI: Uri get() = Uri.Builder()
                .scheme("content")
                .authority(AUTHORITY)
                .path(ITEMS_PATH)
                .build()

        val ITEMS_CODE: Int get() = 1
        val ITEM_BY_ID_CODE: Int get() = 2
    }

    private lateinit var syncDb: SyncDb
    private lateinit var matcher: UriMatcher

    override fun onCreate(): Boolean {
        syncDb = SyncDb(context)
        matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(AUTHORITY, ITEMS_PATH, ITEMS_CODE)
        matcher.addURI(AUTHORITY, ITEMS_PATH + "/#", ITEM_BY_ID_CODE)
        return true
    }

    override fun insert(
            uri: Uri?,
            values: ContentValues?): Uri {
        val code = matcher.match(uri)
        when (code) {
            ITEMS_CODE -> return insertItem(values)
            else -> throw IllegalArgumentException("Unknown uri: " + uri)
        }
    }

    private fun insertItem(values: ContentValues?): Uri {
        val db = syncDb.writableDatabase
        val id = db.insert(
                SyncItemContract.TABLE,
                null,
                values)
        val uri = ITEMS_URI.buildUpon()
                .appendPath(id.toString())
                .build();
        with(context.contentResolver) {
            notifyChange(ITEMS_URI, null)
            notifyChange(uri, null)
        }
        return uri
    }

    override fun query(
            uri: Uri?,
            projection: Array<out String>?,
            selection: String?,
            selectionArgs: Array<out String>?,
            sortOrder: String?): Cursor {
        val code = matcher.match(uri)
        when (code) {
            ITEMS_CODE -> return queryItems(projection, selection, selectionArgs, sortOrder)
            ITEM_BY_ID_CODE -> return queryItemById(uri!!, projection)
            else -> throw IllegalArgumentException("Unknown uri: " + uri)
        }
    }

    private fun queryItems(
            projection: Array<out String>?,
            selection: String?,
            selectionArgs: Array<out String>?,
            sortOrder: String?): Cursor {
        val db = syncDb.readableDatabase
        val cursor = db.query(
                SyncItemContract.TABLE,
                projection, selection,
                selectionArgs, null,
                null, sortOrder)
        cursor.setNotificationUri(context.contentResolver, ITEMS_URI)
        return cursor
    }

    private fun queryItemById(uri: Uri, projection: Array<out String>?): Cursor {
        val id = uri.lastPathSegment

        val db = syncDb.readableDatabase
        val cursor = db.query(
                SyncItemContract.TABLE,
                projection,
                SyncItemContract.FIELD_ID + "=?",
                arrayOf(id), null, null, null)
        cursor.setNotificationUri(context.contentResolver, uri)
        return cursor
    }

    override fun update(
            uri: Uri?,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun delete(
            uri: Uri?,
            selection: String?,
            selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri?): String {
        return "entity/sync-item"
    }
}