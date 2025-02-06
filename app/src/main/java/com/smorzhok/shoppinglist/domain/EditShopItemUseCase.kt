package com.smorzhok.shoppinglist.domain

import com.smorzhok.shoppinglist.data.ShopItem

class EditShopItemUseCase(private val shopListRepository:ShopListRepository)  {

    fun editShopItem(item: ShopItem){
        shopListRepository.editShopItem(item)
    }
}