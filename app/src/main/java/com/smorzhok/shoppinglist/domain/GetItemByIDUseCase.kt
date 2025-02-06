package com.smorzhok.shoppinglist.domain

import com.smorzhok.shoppinglist.data.ShopItem

class GetItemByIDUseCase(private val shopListRepository:ShopListRepository)  {
    fun getItemById(id:Int): ShopItem {
        return shopListRepository.getItemById(id)
    }
}