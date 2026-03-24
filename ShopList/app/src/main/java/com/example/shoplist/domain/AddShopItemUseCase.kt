package com.example.shoplist.domain

class AddShopItemUseCase(private val repository: ShopItemRepository) {

    suspend fun addShopItem(shopItem: ShopItem){
        repository.addShopItem(shopItem)
    }

}