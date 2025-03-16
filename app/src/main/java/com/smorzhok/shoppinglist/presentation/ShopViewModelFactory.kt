package com.smorzhok.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smorzhok.shoppinglist.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ShopViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards
    Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}