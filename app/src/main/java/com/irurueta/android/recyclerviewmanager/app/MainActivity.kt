package com.irurueta.android.recyclerviewmanager.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irurueta.android.recyclerviewmanager.RecyclerViewManager
import java.util.Random

class MainActivity : ComponentActivity() {

    private var recyclerView: RecyclerView? = null

    private var button: Button? = null

    private val adapter = ItemAdapter(emptyList())

    private val manager = RecyclerViewManager<ItemViewHolder, Item>(
        adapter,
        { item1, item2 -> item1.id == item2.id },
        { item1, item2 -> item1.name == item2.name }
    )


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