package com.smorzhok.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smorzhok.shoppinglist.R

class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameTV = view.findViewById<TextView>(R.id.tv_name)
    val countTV = view.findViewById<TextView>(R.id.tv_count)
}