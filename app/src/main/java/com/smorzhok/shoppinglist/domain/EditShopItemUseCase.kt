package com.smorzhok.shoppinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor(private val shopListRepository:ShopListRepository)  {

    suspend fun editShopItem(item: ShopItem){
        shopListRepository.editShopItem(item)
    }
}