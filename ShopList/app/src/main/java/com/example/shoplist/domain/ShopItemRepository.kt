package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopItemRepository {

    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun removeShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemId: Int): ShopItem

    fun getShoplist(): LiveData<List<ShopItem>>

}