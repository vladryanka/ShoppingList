package com.smorzhok.shoppinglist.domain

import com.smorzhok.shoppinglist.data.ShopItem

class GetShopListUseCase(private val shopListRepository:ShopListRepository)  {
    fun getShopList():List<ShopItem>{
        return shopListRepository.getShopList()
    }
}