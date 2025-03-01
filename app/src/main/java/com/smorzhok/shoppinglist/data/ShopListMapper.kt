package com.smorzhok.shoppinglist.data

import com.smorzhok.shoppinglist.domain.ShopItem

class ShopListMapper {
    fun mapEntityToDbModel(shopItem: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            shopItem.id,
            shopItem.name,
            shopItem.count,
            shopItem.enabled
        )
    }

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel): ShopItem {
        return ShopItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            enabled = shopItemDbModel.enabled
        )
    }

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>): List<ShopItem> =
        list.map { mapDbModelToEntity(it) }
}