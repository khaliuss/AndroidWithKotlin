package com.example.shoplist.domain

class RemoveShopItemUseCase(val repository: ShopItemRepository) {

    fun removeShopItem(shopItem: ShopItem){
        repository.removeShopItem(shopItem)
    }

}