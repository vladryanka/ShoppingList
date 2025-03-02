package com.smorzhok.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.smorzhok.shoppinglist.data.ShopListRepositoryImpl
import com.smorzhok.shoppinglist.domain.DeleteShopItemUseCase
import com.smorzhok.shoppinglist.domain.EditShopItemUseCase
import com.smorzhok.shoppinglist.domain.GetShopListUseCase
import com.smorzhok.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(item: ShopItem) {
        viewModelScope.launch { deleteShopItemUseCase.deleteShopItem(item) }

    }

     fun changeEnableState(item: ShopItem) {
        val newItem = ShopItem(item.name, item.count, !item.enabled, item.id)
         viewModelScope.launch { editShopItemUseCase.editShopItem(newItem) }

    }

}
