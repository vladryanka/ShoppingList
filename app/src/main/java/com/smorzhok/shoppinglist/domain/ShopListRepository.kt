package com.smorzhok.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun deleteShopItem(item: ShopItem)
    suspend fun editShopItem(item: ShopItem)
    suspend fun getItemById(id:Int): ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun addShopItem(shopItem: ShopItem)
}