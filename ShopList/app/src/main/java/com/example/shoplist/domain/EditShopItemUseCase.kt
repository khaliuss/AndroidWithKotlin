package com.example.shoplist.domain

class EditShopItemUseCase(private val repository: ShopItemRepository) {

    fun editShopItem(shopItem: ShopItem){
        repository.editShopItem(shopItem)
    }

}