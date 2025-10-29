package com.example.redblue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RectAdapter(private val items: List<Int>) :
    RecyclerView.Adapter<RectAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rectangle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ctx = holder.itemView.context
        val colorRes = if (position % 2 == 1)
            R.color.evenColor else R.color.oddColor
        holder.text.setBackgroundColor(ctx.getColor(colorRes))
        holder.text.text = (position + 1).toString()
    }

    override fun getItemCount() = items.size
}
