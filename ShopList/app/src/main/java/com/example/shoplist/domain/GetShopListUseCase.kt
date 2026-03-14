package com.example.shoplist.domain

class GetShopListUseCase(private val repository: ShopItemRepository) {

    fun getShopList(): List<ShopItem>{
        return repository.getShoplist()
    }

}