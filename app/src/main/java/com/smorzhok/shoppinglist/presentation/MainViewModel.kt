package com.smorzhok.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smorzhok.shoppinglist.data.ShopListRepositoryImpl
import com.smorzhok.shoppinglist.domain.DeleteShopItemUseCase
import com.smorzhok.shoppinglist.domain.EditShopItemUseCase
import com.smorzhok.shoppinglist.domain.GetShopListUseCase
import com.smorzhok.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: ShopListRepositoryImpl,
    private val getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(item: ShopItem) {
        viewModelScope.launch { deleteShopItemUseCase.deleteShopItem(item) }

    }

    fun changeEnableState(item: ShopItem) {
        val newItem = ShopItem(item.name, item.count, !item.enabled, item.id)
        viewModelScope.launch { editShopItemUseCase.editShopItem(newItem) }

    }

}
