package com.example.shoplist.domain

class GetShopItemUseCase(private val repository: ShopItemRepository) {

    suspend fun getShopItem(shopId: Int): ShopItem {
        return repository.getShopItem(shopId)
    }

}