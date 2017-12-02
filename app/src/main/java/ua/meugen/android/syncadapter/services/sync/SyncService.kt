package ua.meugen.android.syncadapter.services.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by meugen on 02.12.2017.
 */
class SyncService: Service() {

    private lateinit var syncAdapter: SyncAdapter

    override fun onCreate() {
        super.onCreate()
        syncAdapter = SyncAdapter(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        return syncAdapter.syncAdapterBinder
    }
}