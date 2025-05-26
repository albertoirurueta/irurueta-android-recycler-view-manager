package com.irurueta.android.recyclerviewmanager.app

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(var items: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val v = ItemView(parent.context)
        return ItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.view.itemName = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}