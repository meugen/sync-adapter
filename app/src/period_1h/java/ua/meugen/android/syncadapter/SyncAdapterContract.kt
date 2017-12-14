package ua.meugen.android.syncadapter

import java.util.concurrent.TimeUnit

object SyncAdapterContract{

    val AUTHORITY: String get() = "ua.meugen.android.syncadapter.period_1h"
    val ACCOUNT_NAME: String get() = "datasync"
    val ACCOUNT_TYPE: String get() = AUTHORITY + "." + ACCOUNT_NAME
    val PERIOD: Long get() = TimeUnit.HOURS.toSeconds(1)
}
