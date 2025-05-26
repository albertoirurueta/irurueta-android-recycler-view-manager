package com.irurueta.android.recyclerviewmanager

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.irurueta.hermes.InsertedListItemChange
import com.irurueta.hermes.ItemComparator
import com.irurueta.hermes.ItemContentComparator
import com.irurueta.hermes.ListItemChange
import com.irurueta.hermes.ListItemChangeAction
import com.irurueta.hermes.MovedListItemChange
import com.irurueta.hermes.RemovedListItemChange
import com.irurueta.hermes.SequentialListItemChangeDetector
import com.irurueta.hermes.UpdatedListItemChange

/**
 * Utility class to simplify recycler view notifications when content changes.
 * This class detects changes between provided lists of data and notifies the adapter accordingly
 * so that proper animations are drawn to add, remove or move items.
 *
 * @param VH type of view holder used by the adapter.
 * @param T type of data held by the adapter.
 * @property adapter adapter holding the data.
 * @property itemComparator comparator to determine if items are the same.
 * @property itemContentComparator comparator to determine if item content is the same.
 */
class RecyclerViewManager<VH : ViewHolder, T>(
    val adapter: RecyclerView.Adapter<VH>,
    itemComparator: ItemComparator<T>,
    itemContentComparator: ItemContentComparator<T>
) {

    /**
     * Detector to determine changes between lists of data.
     */
    val detector = SequentialListItemChangeDetector<T>(itemComparator, itemContentComparator)

    /**
     * Notifies the adapter of changes between old and new lists of data.
     *
     * @param oldItems old list of data.
     * @param newItems new list of data.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun notifyChanges(oldItems: List<T>, newItems: List<T>) {
        Log.d(RecyclerViewManager::class.simpleName, "starting notifyChanges")

        val changes = detector.detectChanges(newItems, oldItems)

        val itemCount = adapter.itemCount
        Log.d(RecyclerViewManager::class.simpleName, "itemCount = $itemCount")

        if (itemCount > 0) {
            for (change in changes) {

                when (change.action) {
                    ListItemChangeAction.INSERTED -> handleInsert(change)
                    ListItemChangeAction.REMOVED -> handleRemove(change)
                    ListItemChangeAction.UPDATED -> handleUpdate(change)
                    ListItemChangeAction.MOVED -> handleMove(change)
                }
            }
        } else {
            adapter.notifyDataSetChanged()
        }

        Log.d(RecyclerViewManager::class.simpleName, "ending notifyChanges")
    }

    /**
     * Handles an insert change.
     *
     * @param change the change to handle.
     */
    private fun handleInsert(change: ListItemChange) {
        val insertChange = change as InsertedListItemChange<*>
        val pos = insertChange.newPosition

        Log.d(
            RecyclerViewManager::class.simpleName,
            "before adapter.notifyItemInserted: pos = $pos"
        )
        adapter.notifyItemInserted(pos)
        Log.d(
            RecyclerViewManager::class.simpleName,
            "after adapter.notifyItemInserted: pos = $pos"
        )
    }

    /**
     * Handles a remove change.
     *
     * @param change the change to handle.
     */
    private fun handleRemove(change: ListItemChange) {
        val removeChange = change as RemovedListItemChange<*>
        val pos = removeChange.oldPosition

        Log.d(
            RecyclerViewManager::class.simpleName,
            "before adapter.notifyItemRemoved: pos = $pos"
        )
        adapter.notifyItemRemoved(pos)
        Log.d(
            RecyclerViewManager::class.simpleName,
            "after adapter.notifyItemRemoved: pos = $pos"
        )
    }

    /**
     * Handles an update change.
     *
     * @param change the change to handle.
     */
    private fun handleUpdate(change: ListItemChange) {
        val updateChange = change as UpdatedListItemChange<*>
        val pos = updateChange.position

        Log.d(
            RecyclerViewManager::class.simpleName,
            "before adapter.notifyItemChanged: pos = $pos"
        )
        adapter.notifyItemChanged(pos)
        Log.d(
            RecyclerViewManager::class.simpleName,
            "after adapter.notifyItemChanged: pos = $pos"
        )
    }

    /**
     * Handles a move change.
     *
     * @param change the change to handle.
     */
    private fun handleMove(change: ListItemChange) {
        val moveChange = change as MovedListItemChange<*>

        Log.d(
            RecyclerViewManager::class.simpleName,
            "before adapter.notifyItemMoved: from = ${moveChange.oldPosition}, " +
                    "to = ${moveChange.newPosition}"
        )
        adapter.notifyItemMoved(moveChange.oldPosition, moveChange.newPosition)
        Log.d(
            RecyclerViewManager::class.simpleName,
            "after adapter.notifyItemMoved: from = ${moveChange.oldPosition}, " +
                    "to = ${moveChange.newPosition}"
        )
    }
}