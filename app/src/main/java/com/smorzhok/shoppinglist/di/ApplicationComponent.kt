package com.smorzhok.shoppinglist.di

import android.app.Application
import com.smorzhok.shoppinglist.data.ShopListProvider
import com.smorzhok.shoppinglist.presentation.MainActivity
import com.smorzhok.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ShopItemFragment)
    fun inject(provider: ShopListProvider)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }

}