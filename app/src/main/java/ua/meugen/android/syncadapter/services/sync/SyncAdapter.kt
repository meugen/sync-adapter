package ua.meugen.android.syncadapter.services.sync

import android.accounts.Account
import android.content.*
import android.os.Bundle
import ua.meugen.android.syncadapter.db.SyncItemContract
import ua.meugen.android.syncadapter.provider.SyncProvider

/**
 * Created by meugen on 02.12.2017.
 */

class SyncAdapter constructor(context: Context) : AbstractThreadedSyncAdapter(context, true) {

    override fun onPerformSync(
            account: Account?,
            extras: Bundle?,
            authority: String?,
            provider: ContentProviderClient?,
            syncResult: SyncResult?) {
        val values = ContentValues()
        values.put(SyncItemContract.FIELD_TIME, System.currentTimeMillis())
        provider?.insert(SyncProvider.ITEMS_URI, values)
    }
}
