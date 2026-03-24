package com.example.shoplist.data.room

import com.example.shoplist.domain.ShopItem

class ShopListMapper {

    fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDBModel(
        id = shopItem.id,
        title = shopItem.title,
        count = shopItem.count,
        isEnable = shopItem.isEnable
    )

    fun mapDbModelToEntity(shopItemDBModel: ShopItemDBModel) = ShopItem(
        id = shopItemDBModel.id,
        title = shopItemDBModel.title,
        count = shopItemDBModel.count,
        isEnable = shopItemDBModel.isEnable
    )


    fun mapListDbModelToEntityList(list: List<ShopItemDBModel>) = list.map {
        mapDbModelToEntity(it)
    }
}