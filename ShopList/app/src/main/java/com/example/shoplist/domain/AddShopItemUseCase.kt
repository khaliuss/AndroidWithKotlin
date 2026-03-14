package com.example.shoplist.domain

class AddShopItemUseCase(private val repository: ShopItemRepository) {

    fun addShopItem(shopItem: ShopItem){
        repository.addShopItem(shopItem)
    }

}