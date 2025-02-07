package com.smorzhok.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smorzhok.shoppinglist.data.ShopItem
import com.smorzhok.shoppinglist.data.ShopListRepositoryImpl
import com.smorzhok.shoppinglist.domain.DeleteShopItemUseCase
import com.smorzhok.shoppinglist.domain.EditShopItemUseCase
import com.smorzhok.shoppinglist.domain.GetShopListUseCase

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl()
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(item: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(item)
    }

     fun changeEnableState(item: ShopItem) {
        val newItem = ShopItem(item.name, item.count, !item.enabled, item.id)
        editShopItemUseCase.editShopItem(item)
    }

}
