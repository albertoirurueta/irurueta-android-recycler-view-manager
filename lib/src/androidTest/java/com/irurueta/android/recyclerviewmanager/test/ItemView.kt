package com.irurueta.android.recyclerviewmanager.test

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView

class ItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var nameView: TextView? = null

    var itemName: String?
        get() = nameView?.text?.toString()
        set(value) {
            nameView?.text = value
        }

    init {
        // Load layout
        LayoutInflater.from(context).inflate(R.layout.item_view, this)

        nameView = findViewById(R.id.item_name)
    }
}