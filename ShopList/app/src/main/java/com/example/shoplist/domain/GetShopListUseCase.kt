package com.example.shoplist.domain

class GetShopListUseCase(val repository: ShopItemRepository) {

    fun getShopList(): List<ShopItem>{
        return repository.getShoplist()
    }

}