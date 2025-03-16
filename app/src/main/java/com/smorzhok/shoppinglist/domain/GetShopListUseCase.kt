package com.smorzhok.shoppinglist.domain

import android.util.Log
import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(private val shopListRepository:ShopListRepository)  {
    fun getShopList(): LiveData<List<ShopItem>> {
        Log.d("Doing", shopListRepository.getShopList().value.toString())
        return shopListRepository.getShopList()
    }
}