fun updateItems(newItems: List<Item>) {
    val oldItems = adapter.items

    // 1. Hand the new data to the adapter first: getItemCount() must already
    //    return the size of the new list.
    adapter.items = newItems

    // 2. Compute the differences and notify the adapter (old list first!).
    manager.notifyChanges(oldItems, newItems)
}
