package ua.meugen.android.syncadapter

import android.accounts.Account
import android.accounts.AccountManager
import android.content.ContentResolver
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import ua.meugen.android.syncadapter.adapters.SyncAdapter
import ua.meugen.android.syncadapter.db.SyncItemContract
import ua.meugen.android.syncadapter.provider.SyncProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        val AUTHORITY: String get() = "ua.meugen.android.syncadapter"
        val ACCOUNT_TYPE: String get() = "ua.meugen.android.syncadapter.datasync"
        val PERIOD: Long get() = TimeUnit.HOURS.toSeconds(1)
    }

    private lateinit var adapter: SyncAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = SyncAdapter(this)
        recycler.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))
        recycler.adapter = adapter

        supportLoaderManager.initLoader(0, null, this)

        val account = createAccount()
        if (account != null) {
            ContentResolver.setSyncAutomatically(account, AUTHORITY, true)
            ContentResolver.addPeriodicSync(account, AUTHORITY, Bundle.EMPTY, PERIOD)
        }
    }

    private fun createAccount(): Account? {
        val account = Account("datasync", ACCOUNT_TYPE)
        val result = AccountManager.get(this).addAccountExplicitly(account, null, null)
        return if (result) account else null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val loader = CursorLoader(this)
        loader.projection = arrayOf(SyncItemContract.FIELD_ID, SyncItemContract.FIELD_TIME)
        loader.selection = null
        loader.selectionArgs = null
        loader.sortOrder = null
        loader.uri = SyncProvider.ITEMS_URI
        return loader
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        adapter.swapCursor(data!!)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {}
}
