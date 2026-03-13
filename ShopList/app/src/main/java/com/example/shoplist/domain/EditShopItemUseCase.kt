package com.example.shoplist.domain

class EditShopItemUseCase(val repository: ShopItemRepository) {

    fun editShopItem(shopItem: ShopItem){
        repository.editShopItem(shopItem)
    }

}