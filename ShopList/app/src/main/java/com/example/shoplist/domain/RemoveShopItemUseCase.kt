package com.example.shoplist.domain

class RemoveShopItemUseCase(private val repository: ShopItemRepository) {

    suspend fun removeShopItem(shopItem: ShopItem){
        repository.removeShopItem(shopItem)
    }

}