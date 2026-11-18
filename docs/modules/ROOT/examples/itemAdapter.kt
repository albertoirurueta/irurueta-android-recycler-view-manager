class ItemAdapter(var items: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(ItemView(parent.context))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.view.itemName = items[position].name
    }

    override fun getItemCount(): Int = items.size
}

class ItemViewHolder(val view: ItemView) : RecyclerView.ViewHolder(view)
