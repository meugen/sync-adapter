package ua.meugen.android.syncadapter.adapters

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*
import ua.meugen.android.syncadapter.R
import java.text.DateFormat
import java.util.*

/**
 * Created by meugen on 02.12.2017.
 */
class SyncItemsAdapter(context: Context): RecyclerView.Adapter<SyncViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var cursor: Cursor? = null

    fun swapCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SyncViewHolder {
        val view = inflater.inflate(R.layout.item_sync, parent, false)
        return SyncViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: SyncViewHolder?, position: Int) {
        if (cursor!!.moveToPosition(position)) {
            holder!!.bind(cursor!!)
        }
    }
}

class SyncViewHolder(view: View): RecyclerView.ViewHolder(view) {

    companion object {
        val FORMAT = DateFormat.getDateTimeInstance()
    }

    private val textView = view.findViewById<TextView>(R.id.text)

    fun bind(cursor: Cursor) {
        textView.text = FORMAT.format(Date(cursor.getLong(1)))
    }
}