package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopItemRepository {

    fun addShopItem(shopItem: ShopItem)

    fun removeShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int): ShopItem

    fun getShoplist(): LiveData<List<ShopItem>>

}