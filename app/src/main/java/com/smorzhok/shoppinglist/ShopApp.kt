package com.smorzhok.shoppinglist

import android.app.Application
import com.smorzhok.shoppinglist.di.DaggerApplicationComponent

class ShopApp: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}