package com.smorzhok.shoppinglist.domain

import com.smorzhok.shoppinglist.data.ShopItem

interface ShopListRepository {
    fun deleteShopItem(item: ShopItem)
    fun editShopItem(item: ShopItem)
    fun getItemById(id:Int): ShopItem
    fun getShopList():List<ShopItem>
    fun addShopItem(shopItem: ShopItem)
}