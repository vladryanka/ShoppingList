package com.smorzhok.shoppinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun shopListDao(): ShopListDao

    companion object {
        private var INSTANCE: ShopDatabase? = null
        private var LOCK = Any()
        private const val DB_NAME = "ShopDatabase"

        fun getInstance(application: Application): ShopDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val db = Room.databaseBuilder(application, ShopDatabase::class.java, DB_NAME)
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}