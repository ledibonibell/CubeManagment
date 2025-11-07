package com.example.redblue

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// адаптер для отображения квадратов в RecyclerView
class RectAdapter(private val items: MutableList<Int>) :
    RecyclerView.Adapter<RectAdapter.ViewHolder>() {

    // держатель ссылки на TextView внутри карточки
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.itemText)
    }

    // создаёт макет элемента (item_rectangle.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rectangle, parent, false)
        return ViewHolder(view)
    }

    // заполняет квадратики
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ctx = holder.itemView.context
        val colorRes = if (position % 2 == 1)
            R.color.evenColor else R.color.oddColor
        holder.text.setBackgroundColor(ctx.getColor(colorRes))
        holder.text.text = (position + 1).toString()
    }

    // общее количество элементов в списке
    override fun getItemCount() = items.size
}
