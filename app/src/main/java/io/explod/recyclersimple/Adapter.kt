package io.explod.recyclersimple

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


data class AdapterData(val id: Long, val name: String, var checked: Boolean)

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    interface Listener {
        fun onItemClicked(data: AdapterData, position: Int)
    }

    private var listener: Listener? = null

    private var items: List<AdapterData>? = null

    init {
        // easy optimization tip, requires implementing getItemId
        setHasStableIds(true)
    }

    fun setAdapterData(items: List<AdapterData>?) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    operator fun get(position: Int): AdapterData? {
        // override the "get" operator to support: val item = this[position]
        return items?.getOrNull(position)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun getItemId(position: Int): Long {
        return this[position]?.id ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val context = parent?.context ?: return null
        val view = LayoutInflater.from(context).inflate(R.layout.item_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // no allocations!
        val item = this[position] ?: return
        holder.text.text = item.name
        holder.checked.text = when (item.checked) {
            true -> "CHECKED"
            false -> "NOT CHECKED"
        }
    }

    private fun notifyItemClicked(position: Int) {
        // let the listener handle it
        val item = this[position] ?: return
        listener?.onItemClicked(item, position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val text: TextView
        val checked: TextView

        init {
            view.setOnClickListener(this)
            text = view.findViewById<TextView>(R.id.text_text)
            checked = view.findViewById<TextView>(R.id.text_checked)
        }

        override fun onClick(p0: View?) {
            // let the adapter handle it
            notifyItemClicked(adapterPosition)
        }

    }

}