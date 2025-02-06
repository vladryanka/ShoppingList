package com.smorzhok.shoppinglist.domain

class GetItemByIDUseCase(private val shopListRepository:ShopListRepository)  {
    fun getItemById(id:Int):ShopItem{
        return shopListRepository.getItemById(id)
    }
}