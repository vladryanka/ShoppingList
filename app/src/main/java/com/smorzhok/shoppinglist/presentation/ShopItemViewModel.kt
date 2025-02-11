package com.smorzhok.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smorzhok.shoppinglist.data.ShopItem
import com.smorzhok.shoppinglist.data.ShopListRepositoryImpl
import com.smorzhok.shoppinglist.domain.AddShopItemUseCase
import com.smorzhok.shoppinglist.domain.EditShopItemUseCase
import com.smorzhok.shoppinglist.domain.GetItemByIDUseCase

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getItemByIDUseCase = GetItemByIDUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean> get() = _errorInputCount

    private var _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem> get() = _shopItem

    private val _isSaved = MutableLiveData<Unit>()
    val isSaved: LiveData<Unit> get() = _isSaved

    fun addItem(name: String?, count: String?) {
        val newName = correctName(name)
        val newCount = correctCount(count)
        if (validateInput(newName, newCount)) {
            addShopItemUseCase.addShopItem(ShopItem(newName, newCount, true))
            finishWork()
        }
    }

    fun editItem(name: String?, count: String?) {
        val newName = correctName(name)
        val newCount = correctCount(count)
        if (validateInput(newName, newCount)) {
            _shopItem.value?.let {
                val newShopItem = it.copy(newName,newCount)
                editShopItemUseCase.editShopItem(newShopItem)
                finishWork()
            }
        }
    }

    private fun getItemByID(id: Int) {
        _shopItem.value = getItemByIDUseCase.getItemById(id)
    }

    private fun correctName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun correctCount(count: String?): Int {
        val newCount = try {
            count?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
        return newCount
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
            _errorInputName.value = true
        }
        if (count <= 0) {
            result = false
            _errorInputCount.value = true
        }
        return result
    }

    private fun resetValueInputName(name: String) {
        _errorInputName.value = false
    }

    private fun resetValueInputCount(count: String) {
        _errorInputCount.value = false
    }

    private fun finishWork(){
        _isSaved.value = Unit
    }
}