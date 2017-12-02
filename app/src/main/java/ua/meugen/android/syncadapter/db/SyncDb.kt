package ua.meugen.android.syncadapter.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by meugen on 02.12.2017.
 */
class SyncDb(context: Context): SQLiteOpenHelper(context, "sync.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE " + SyncItemContract.TABLE + " ("
                + SyncItemContract.FIELD_ID + " INTEGER PRIMARY KEY, "
                + SyncItemContract.FIELD_TIME + " TIMESTAMP NOT NULL)");
    }

    override fun onUpgrade(
            db: SQLiteDatabase?,
            oldVersion: Int,
            newVersion: Int) {
        db!!.execSQL("DROP TABLE " + SyncItemContract.TABLE)
        onCreate(db)
    }
}