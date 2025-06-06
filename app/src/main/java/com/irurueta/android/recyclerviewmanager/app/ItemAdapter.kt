/*
 * Copyright (C) 2025 Alberto Irurueta Carro (alberto@irurueta.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.irurueta.android.recyclerviewmanager.app

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter to hold a list of items.
 *
 * @param items list of items.
 */
class ItemAdapter(var items: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {

    /**
     * Creates a view holder for an item.
     *
     * @param parent parent view group.
     * @param viewType type of view.
     * @return view holder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val v = ItemView(parent.context)
        return ItemViewHolder(v)
    }

    /**
     * Binds an item to a view holder.
     *
     * @param holder view holder.
     * @param position item position.
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.view.itemName = items[position].name
    }

    /**
     * Gets the number of items in the adapter.
     *
     * @return number of items.
     */
    override fun getItemCount(): Int {
        return items.size
    }
}