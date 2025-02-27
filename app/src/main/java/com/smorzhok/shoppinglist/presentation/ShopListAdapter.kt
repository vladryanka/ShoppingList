package com.smorzhok.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.ViewDataBindingKtx
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smorzhok.shoppinglist.R
import com.smorzhok.shoppinglist.data.ShopItem
import com.smorzhok.shoppinglist.databinding.ShopItemDisabledBinding
import com.smorzhok.shoppinglist.databinding.ShopItemEnabledBinding
import java.util.zip.Inflater

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemShortClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            IS_ENABLED -> R.layout.shop_item_enabled
            IS_DISABLED -> R.layout.shop_item_disabled
            else -> throw IllegalArgumentException("Unknown layout")
        }
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layout,
                parent,
                false
            )
        return ShopItemViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item.enabled == true)
            return IS_ENABLED
        else return IS_DISABLED
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        when (binding) {
            is ShopItemDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
            is ShopItemEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
        }
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemShortClickListener?.invoke(shopItem)
        }
    }

    companion object {
        const val IS_ENABLED = 0
        const val IS_DISABLED = 1
        const val MAX_POOL_SIZE = 15
    }
}