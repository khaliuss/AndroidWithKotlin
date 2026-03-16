package com.example.shoplist.domain

class RemoveShopItemUseCase(private val repository: ShopItemRepository) {

    fun removeShopItem(shopItem: ShopItem){
        repository.removeShopItem(shopItem)
    }

}