class ItemsActivity : ComponentActivity() {

    private val viewModel: ItemsViewModel by viewModels()
    private val adapter = ItemAdapter(emptyList())
    private val manager = RecyclerViewManager<ItemViewHolder, Item>(
        adapter,
        { a, b -> a.id == b.id },
        { a, b -> a.name == b.name }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // StateFlow<List<Item>> exposed by the view model; collected on the main thread
                viewModel.items.collect { newItems ->
                    val oldItems = adapter.items
                    adapter.items = newItems
                    manager.notifyChanges(oldItems, newItems)
                }
            }
        }
    }
}
