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

package com.irurueta.android.recyclerviewmanager.test

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.irurueta.android.recyclerviewmanager.RecyclerViewManager
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewManagerTest {

    @get:Rule
    val rule = activityScenarioRule<RecyclerViewManagerActivity>()

    private var activity: RecyclerViewManagerActivity? = null
    private var view: RecyclerView? = null

    @Before
    fun setUp() {
        rule.scenario.onActivity { activity ->
            this.activity = activity
            view = activity.findViewById(R.id.recycler_view_test)
        }
    }

    @After
    fun tearDown() {
        view = null
        activity = null
    }

    @Test
    fun notifyChanges_whenEmptyAdapter_refreshesRecyclerView() {
        // create data, adapter and manager
        val oldItems = emptyList<Item>()
        val newItems = emptyList<Item>()
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenNotEmptyAndNoChanges_makesNoAction() {
        // create data, adapter and manager
        val item = Item(1, "Item 1")
        val oldItems = listOf(item)
        val newItems = listOf(item)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set adapter and notify changes
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenInsert_notifiesInsertedItem() {
        // create data, adapter and manager
        val item = Item(1, "Item 1")
        val oldItems = emptyList<Item>()
        val newItems = listOf(item)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenInsertAtBeginning_notifiesInsertedItem() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item1)
        val newItems = listOf(item2, item1)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenInsertAtEnd_notifiesInsertedItem() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item1)
        val newItems = listOf(item1, item2)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenMultipleInserts_notifiesInsertedItems() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item3 = Item(3, "Item 3")
        val oldItems = listOf(item1)
        val newItems = listOf(item3, item1, item2)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenRemoveAtBeginning_notifiesRemovedItem() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item2, item1)
        val newItems = listOf(item1)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenRemoveAtEnd_notifiesRemovedItem() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item1, item2)
        val newItems = listOf(item1)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenMultipleRemoves_notifiesRemovedItems() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item3 = Item(3, "Item 3")
        val oldItems = listOf(item3, item1, item2)
        val newItems = listOf(item1)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenSwap_notifiesMovedItem() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item1, item2)
        val newItems = listOf(item2, item1)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenReversed_notifiesMovedItems() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item3 = Item(3, "Item 3")
        val oldItems = listOf(item1, item2, item3)
        val newItems = listOf(item3, item2, item1)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenUpdate_notifiesChangedItem() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item2b = Item(2, "Item 2b")
        val oldItems = listOf(item1, item2)
        val newItems = listOf(item1, item2b)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    @Test
    fun notifyChanges_whenMultipleChanges_notifiesChangedItems() {
        // create data, adapter and manager
        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item2b = Item(2, "Item 2b")
        val item3 = Item(3, "Item 3")
        val item4 = Item(4, "Item 4")
        val item5 = Item(5, "Item 5")
        val oldItems = listOf(item1, item2, item3, item4)
        val newItems = listOf(item3, item2b, item1, item5)
        val adapter = ItemAdapter(oldItems)
        val manager = RecyclerViewManager<ItemViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )

        // set recycler view with initial data
        activity?.runOnUiThread {
            view?.layoutManager = LinearLayoutManager(activity)
            view?.adapter = adapter
        }

        Thread.sleep(SLEEP)

        // change data and notify changes
        activity?.runOnUiThread {
            adapter.items = newItems
            manager.notifyChanges(oldItems, newItems)
        }

        Thread.sleep(SLEEP)
    }

    private companion object {
        const val SLEEP = 1000L
    }
}