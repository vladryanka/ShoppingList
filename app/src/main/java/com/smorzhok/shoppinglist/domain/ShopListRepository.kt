package com.smorzhok.shoppinglist.domain

interface ShopListRepository {
    fun deleteShopItem(item:ShopItem)
    fun editShopItem(item:ShopItem)
    fun getItemById(id:Int):ShopItem
    fun getShopList():List<ShopItem>
    fun addShopItem(shopItem: ShopItem)
}