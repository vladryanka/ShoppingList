package com.smorzhok.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smorzhok.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init{
        for (i in 0..10){
            val item = ShopItem("Name $i",i+10 ,true,i)
            shopList.add(item)
        }
    }

    override fun deleteShopItem(item: ShopItem) {
        shopList.remove(item)
        updateLD()
    }

    override fun editShopItem(item: ShopItem) {
        val oldShopItem = getItemById(item.id)
        shopList.remove(oldShopItem)
        addShopItem(item)
    }

    override fun getItemById(id: Int): ShopItem {
        return shopList.find { it.id == id }
            ?: throw RuntimeException("Element with $id is not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID)
            shopItem.id = autoIncrementId++
        shopList.add(shopItem)
        updateLD()
    }

    private fun updateLD(){
        shopListLD.value = shopList.toList()
    }
}