package com.example.shoplist.domain

class EditShopItemUseCase(private val repository: ShopItemRepository) {

    suspend fun editShopItem(shopItem: ShopItem){
        repository.editShopItem(shopItem)
    }

}