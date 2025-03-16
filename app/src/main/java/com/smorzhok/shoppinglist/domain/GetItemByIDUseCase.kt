package com.smorzhok.shoppinglist.domain

import javax.inject.Inject

class GetItemByIDUseCase @Inject constructor(private val shopListRepository:ShopListRepository)  {
    suspend fun getItemById(id:Int): ShopItem {
        return shopListRepository.getItemById(id)
    }
}