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

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irurueta.android.recyclerviewmanager.RecyclerViewManager
import java.util.Random

/**
 * Main activity.
 */
class MainActivity : ComponentActivity() {

    /**
     * Recycler view.
     */
    private var recyclerView: RecyclerView? = null

    /**
     * Button to update items.
     */
    private var button: Button? = null

    /**
     * Adapter to hold items.
     */
    private val adapter = ItemAdapter(emptyList())

    /**
     * Manager to handle recycler view changes.
     */
    private val manager = RecyclerViewManager<ItemViewHolder, Item>(
        adapter,
        { item1, item2 -> item1.id == item2.id },
        { item1, item2 -> item1.name == item2.name }
    )

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        button = findViewById(R.id.button)

        // setup recycler view
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter

        // setup button
        button?.setOnClickListener { _ -> updateItems() }
    }

    /**
     * Updates items when activity is resumed.
     */
    private fun updateItems() {
        // generate new items
        val oldItems = adapter.items
        val newItems = generateItems()

        Log.d(MainActivity::class.simpleName, "newItems = $newItems")

        // update the adapter with the new items before notifying the adapter
        adapter.items = newItems
        // compute changes and notify the adapter
        manager.notifyChanges(oldItems, newItems)
    }

    /**
     * Generates a list of items.
     *
     * @return list of items.
     */
    private fun generateItems(): List<Item> {
        val random = Random()
        val maxItems = random.nextInt(10)

        val reversed = random.nextBoolean()
        return (0..maxItems).map {
            val withStar = random.nextBoolean()
            val star = if (withStar) " *" else ""
            val id = if (reversed) maxItems - it else it
            Item(id, "Item $id$star")
        }
    }
}