package com.smorzhok.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository:ShopListRepository)  {

    suspend fun editShopItem(item: ShopItem){
        shopListRepository.editShopItem(item)
    }
}