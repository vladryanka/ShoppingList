package com.smorzhok.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {
    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Query("DELETE FROM shop_items WHERE id = :id")
    suspend fun deleteShopItem(id: Int)

    @Query("SELECT * FROM shop_items WHERE id = :id LIMIT 1")
    suspend fun getByIdShopItem(id: Int): ShopItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(shopItem: ShopItemDbModel)
}