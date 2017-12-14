package ua.meugen.android.syncadapter

import java.util.concurrent.TimeUnit

/**
 * Created by meugen on 14.12.2017.
 */
object SyncAdapterContract {

    val AUTHORITY: String get() = "ua.meugen.android.syncadapter.period_6h"
    val ACCOUNT_NAME: String get() = "datasync"
    val ACCOUNT_TYPE: String get() = AUTHORITY + "." + ACCOUNT_NAME
    val PERIOD: Long get() = TimeUnit.HOURS.toSeconds(6)
}