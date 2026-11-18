private val adapter = ItemAdapter(emptyList())

private val manager = RecyclerViewManager<ItemViewHolder, Item>(
    adapter,
    // ItemComparator: are both items the same entity?
    { item1, item2 -> item1.id == item2.id },
    // ItemContentComparator: is the content of the same entity unchanged?
    { item1, item2 -> item1.name == item2.name }
)
