package com.smorzhok.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smorzhok.shoppinglist.R
import com.smorzhok.shoppinglist.data.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(shopList,value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onShopItemLongClickListener: ((ShopItem)->Unit)? = null
    var onShopItemShortClickListener: ((ShopItem) -> Unit)? = null

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTV = view.findViewById<TextView>(R.id.tv_name)
        val countTV = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            IS_ENABLED -> R.layout.shop_item_enabled
            IS_DISABLED -> R.layout.shop_item_disabled
            else -> throw IllegalArgumentException("Unknown layout")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        if (item.enabled == true)
            return IS_ENABLED
        else return IS_DISABLED
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.nameTV.text = shopItem.name
        holder.countTV.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener{
            onShopItemShortClickListener?.invoke(shopItem)
        }
    }

    companion object {
        const val IS_ENABLED = 0
        const val IS_DISABLED = 1
        const val MAX_POOL_SIZE = 15
    }
}