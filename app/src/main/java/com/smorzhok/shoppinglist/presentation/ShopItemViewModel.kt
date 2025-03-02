package com.smorzhok.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smorzhok.shoppinglist.data.ShopListRepositoryImpl
import com.smorzhok.shoppinglist.domain.AddShopItemUseCase
import com.smorzhok.shoppinglist.domain.EditShopItemUseCase
import com.smorzhok.shoppinglist.domain.GetItemByIDUseCase
import com.smorzhok.shoppinglist.domain.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)
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
        val fieldsValid = validateInput(newName, newCount)
        if (fieldsValid) {
            viewModelScope.launch {
                val shopItem = ShopItem(newName, newCount, true)
                addShopItemUseCase.addShopItem(shopItem)
                withContext(Dispatchers.Main) {
                    finishWork()
                }
            }
        }
    }

    fun editItem(name: String?, count: String?) {
        val newName = correctName(name)
        val newCount = correctCount(count)
        if (validateInput(newName, newCount)) {
            _shopItem.value?.let {
                viewModelScope.launch {
                    val newShopItem = it.copy(name = newName, count = newCount)
                    editShopItemUseCase.editShopItem(newShopItem)
                    withContext(Dispatchers.Main) {
                        finishWork()
                    }
                }
            }
        }
    }

    fun getItemByID(id: Int) {
        viewModelScope.launch { _shopItem.value = getItemByIDUseCase.getItemById(id) }
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

    fun resetValueInputName() {
        _errorInputName.value = false
    }

    fun resetValueInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _isSaved.value = Unit
    }
}