package com.smorzhok.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.smorzhok.shoppinglist.domain.ShopItem
import com.smorzhok.shoppinglist.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {
    private val shopListDao = ShopDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override fun deleteShopItem(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override fun editShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override fun getItemById(id: Int): ShopItem {
        val dbModel = shopListDao.getByIdShopItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
    TODO()
    //return shopListDao.getShopList()
    }

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }
}