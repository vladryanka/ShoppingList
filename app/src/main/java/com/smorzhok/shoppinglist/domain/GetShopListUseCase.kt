package com.smorzhok.shoppinglist.domain

import androidx.lifecycle.LiveData
import com.smorzhok.shoppinglist.data.ShopItem

class GetShopListUseCase(private val shopListRepository:ShopListRepository)  {
    fun getShopList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopList()
    }
}