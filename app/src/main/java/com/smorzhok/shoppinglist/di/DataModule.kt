package com.smorzhok.shoppinglist.di

import android.app.Application
import com.smorzhok.shoppinglist.data.ShopDatabase
import com.smorzhok.shoppinglist.data.ShopListDao
import com.smorzhok.shoppinglist.data.ShopListRepositoryImpl
import com.smorzhok.shoppinglist.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao {
            return ShopDatabase.getInstance(application).shopListDao()
        }
    }

}