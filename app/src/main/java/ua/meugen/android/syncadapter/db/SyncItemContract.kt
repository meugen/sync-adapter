package ua.meugen.android.syncadapter.db

/**
 * Created by meugen on 02.12.2017.
 */
interface SyncItemContract {

    companion object {

        val TABLE: String get() = "items"

        val FIELD_ID: String get() = "id"
        val FIELD_TIME: String get() = "time"
    }
}