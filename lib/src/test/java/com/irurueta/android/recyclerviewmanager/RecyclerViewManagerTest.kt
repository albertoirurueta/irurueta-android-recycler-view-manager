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

package com.irurueta.android.recyclerviewmanager

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecyclerViewManagerTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    private lateinit var manager: RecyclerViewManager<RecyclerView.ViewHolder, Item>

    @Before
    fun setup() {
        manager = RecyclerViewManager<RecyclerView.ViewHolder, Item>(
            adapter,
            { item1, item2 -> item1.id == item2.id },
            { item1, item2 -> item1.name == item2.name }
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun notifyChanges_whenEmptyAdapter_notifiesDataSetChanged() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 0
        every { adapter.notifyDataSetChanged() } just runs

        manager.notifyChanges(emptyList(), emptyList())

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 0")
            adapter.notifyDataSetChanged()
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenNotEmptyAdapterAndNoChanges_makesNoAction() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 1

        val item = Item(1, "Item 1")
        manager.notifyChanges(listOf(item), listOf(item))

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 1")
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }
        verify(exactly = 1) { adapter.itemCount }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenInsert_notifiesInsertedItem() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 1
        every { adapter.notifyItemInserted(any()) } just runs

        val item = Item(1, "Item 1")
        manager.notifyChanges(emptyList(), listOf(item))

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 1")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemInserted: pos = 0"
            )
            adapter.notifyItemInserted(0)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemInserted: pos = 0"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenInsertAtBeginning_notifiesInsertedItem() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 2
        every { adapter.notifyItemInserted(any()) } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item1)
        val newItems = listOf(item2, item1)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 2")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemInserted: pos = 0"
            )
            adapter.notifyItemInserted(0)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemInserted: pos = 0"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenInsertAtEnd_notifiesInsertedItem() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 2
        every { adapter.notifyItemInserted(any()) } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item1)
        val newItems = listOf(item1, item2)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 2")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemInserted: pos = 1"
            )
            adapter.notifyItemInserted(1)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemInserted: pos = 1"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenMultipleInserts_notifiesMultipleItemInserts() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 3
        every { adapter.notifyItemInserted(any()) } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item3 = Item(3, "Item 3")
        val oldItems = listOf(item1)
        val newItems = listOf(item3, item1, item2)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 3")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemInserted: pos = 0"
            )
            adapter.notifyItemInserted(0)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemInserted: pos = 0"
            )
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemInserted: pos = 2"
            )
            adapter.notifyItemInserted(2)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemInserted: pos = 2"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenRemoveAtBeginning_notifiesRemovedItem() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 1
        every { adapter.notifyItemRemoved(any()) } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item2, item1)
        val newItems = listOf(item1)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 1")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemRemoved: pos = 0"
            )
            adapter.notifyItemRemoved(0)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemRemoved: pos = 0"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenRemoveAtEnd_notifiesRemovedItem() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 1
        every { adapter.notifyItemRemoved(any()) } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item1, item2)
        val newItems = listOf(item1)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 1")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemRemoved: pos = 1"
            )
            adapter.notifyItemRemoved(1)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemRemoved: pos = 1"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenMultipleRemoves_notifiesMultipleItemRemoves() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 1
        every { adapter.notifyItemRemoved(any()) } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item3 = Item(3, "Item 3")
        val oldItems = listOf(item3, item1, item2)
        val newItems = listOf(item1)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 1")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemRemoved: pos = 0"
            )
            adapter.notifyItemRemoved(0)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemRemoved: pos = 0"
            )
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemRemoved: pos = 1"
            )
            adapter.notifyItemRemoved(1)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemRemoved: pos = 1"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenSwap_notifiesMovedItem() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 2
        every {
            adapter.notifyItemMoved(any(), any())
        } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val oldItems = listOf(item1, item2)
        val newItems = listOf(item2, item1)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 2")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemMoved: from = 0, to = 1"
            )
            adapter.notifyItemMoved(0, 1)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemMoved: from = 0, to = 1"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenReversed_notifiesMovedItems() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 3
        every {
            adapter.notifyItemMoved(any(), any())
        } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item3 = Item(3, "Item 3")
        val oldItems = listOf(item1, item2, item3)
        val newItems = listOf(item3, item2, item1)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 3")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemMoved: from = 0, to = 2"
            )
            adapter.notifyItemMoved(0, 2)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemMoved: from = 0, to = 2"
            )
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemMoved: from = 0, to = 1"
            )
            adapter.notifyItemMoved(0, 1)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemMoved: from = 0, to = 1"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenUpdate_notifiesChangedItem() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 2
        every { adapter.notifyItemChanged(any()) } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item2b = Item(2, "Item 2b")
        val oldItems = listOf(item1, item2)
        val newItems = listOf(item1, item2b)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 2")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemChanged: pos = 1"
            )
            adapter.notifyItemChanged(1)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemChanged: pos = 1"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    @Test
    fun notifyChanges_whenMultipleChanges_makesMultipleNotifications() {
        mockkStatic(Log::class)
        every { Log.d(RecyclerViewManager::class.simpleName, any()) } returns 1
        every { adapter.itemCount } returns 4
        every { adapter.notifyItemInserted(any()) } just runs
        every { adapter.notifyItemRemoved(any()) } just runs
        every {
            adapter.notifyItemMoved(any(), any())
        } just runs
        every { adapter.notifyItemChanged(any()) } just runs

        val item1 = Item(1, "Item 1")
        val item2 = Item(2, "Item 2")
        val item2b = Item(2, "Item 2b")
        val item3 = Item(3, "Item 3")
        val item4 = Item(4, "Item 4")
        val item5 = Item(5, "Item 5")
        val oldItems = listOf(item1, item2, item3, item4)
        val newItems = listOf(item3, item2b, item1, item5)

        manager.notifyChanges(oldItems, newItems)

        verifySequence {
            Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")
            adapter.itemCount
            Log.d(RecyclerViewManager::class.simpleName, "itemCount = 4")
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemRemoved: pos = 3"
            )
            adapter.notifyItemRemoved(3)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemRemoved: pos = 3"
            )
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemInserted: pos = 3"
            )
            adapter.notifyItemInserted(3)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemInserted: pos = 3"
            )
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemMoved: from = 0, to = 2"
            )
            adapter.notifyItemMoved(0, 2)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemMoved: from = 0, to = 2"
            )
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemMoved: from = 0, to = 1"
            )
            adapter.notifyItemMoved(0, 1)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemMoved: from = 0, to = 1"
            )
            Log.d(
                RecyclerViewManager::class.simpleName,
                "before adapter.notifyItemChanged: pos = 1"
            )
            adapter.notifyItemChanged(1)
            Log.d(
                RecyclerViewManager::class.simpleName,
                "after adapter.notifyItemChanged: pos = 1"
            )
            Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
        }

        confirmVerified(adapter)
    }

    private class Item(val id: Int, val name: String)
}