import com.irurueta.hermes.ItemComparator
import com.irurueta.hermes.ItemContentComparator

// Comparators can also be declared as reusable objects
object ItemIdentity : ItemComparator<Item> {
    override fun equals(item1: Item, item2: Item) = item1.id == item2.id
}

object ItemContent : ItemContentComparator<Item> {
    override fun equalContent(item1: Item, item2: Item) = item1 == item2
}

val manager = RecyclerViewManager<ItemViewHolder, Item>(adapter, ItemIdentity, ItemContent)
