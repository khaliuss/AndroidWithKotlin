package com.example.shoplist.domain

class GetShopItemUseCase(val repository: ShopItemRepository) {

    fun getShopItem(shopId: Int): ShopItem {
        return repository.getShopItem(shopId)
    }

}