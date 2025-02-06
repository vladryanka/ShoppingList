package com.smorzhok.shoppinglist.domain

import com.smorzhok.shoppinglist.data.ShopItem

class DeleteShopItemUseCase(private val shopListRepository:ShopListRepository)  {
    fun deleteShopItem(item: ShopItem){
        shopListRepository.deleteShopItem(item)
    }

}