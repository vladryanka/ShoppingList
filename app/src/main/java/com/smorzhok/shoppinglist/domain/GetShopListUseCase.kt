package com.smorzhok.shoppinglist.domain

import android.util.Log
import androidx.lifecycle.LiveData
import com.smorzhok.shoppinglist.data.ShopItem

class GetShopListUseCase(private val shopListRepository:ShopListRepository)  {
    fun getShopList(): LiveData<List<ShopItem>> {
        Log.d("Doing", shopListRepository.getShopList().value.toString())
        return shopListRepository.getShopList()
    }
}