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

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * View to display an item in a recycler view.
 *
 * @param context context of the view.
 * @param attrs attributes of the view.
 * @param defStyleAttr default style attribute of the view.
 */
class ItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    /**
     * Name of the item to be displayed.
     */
    private var nameView: TextView? = null

    /**
     * Gets or sets the name of the item to be displayed.
     */
    var itemName: String?
        get() = nameView?.text?.toString()
        set(value) {
            nameView?.text = value
        }

    /**
     * Initializes the view.
     */
    init {
        // Load layout
        LayoutInflater.from(context).inflate(R.layout.item_view, this)

        nameView = findViewById(R.id.item_name)
    }
}