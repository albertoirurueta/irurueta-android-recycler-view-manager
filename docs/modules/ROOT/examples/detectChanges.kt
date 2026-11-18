import com.irurueta.hermes.InsertedListItemChange
import com.irurueta.hermes.MovedListItemChange
import com.irurueta.hermes.RemovedListItemChange
import com.irurueta.hermes.UpdatedListItemChange

// Note the argument order of the underlying detector: new items first
val changes = manager.detector.detectChanges(newItems, oldItems)

for (change in changes) {
    when (change) {
        is InsertedListItemChange<*> -> Log.d(TAG, "inserted at ${change.newPosition}")
        is RemovedListItemChange<*> -> Log.d(TAG, "removed from ${change.oldPosition}")
        is UpdatedListItemChange<*> -> Log.d(TAG, "updated at ${change.position}")
        is MovedListItemChange<*> ->
            Log.d(TAG, "moved from ${change.oldPosition} to ${change.newPosition}")
    }
}
