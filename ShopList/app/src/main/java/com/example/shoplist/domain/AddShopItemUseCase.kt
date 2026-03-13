package com.example.shoplist.domain

class AddShopItemUseCase(val repository: ShopItemRepository) {

    fun addShopItem(shopItem: ShopItem){
        repository.addShopItem(shopItem)
    }

}