package com.example.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopItemRepositoryImpl
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.GetShopListUseCase
import com.example.shoplist.domain.RemoveShopItemUseCase
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopItemRepository

class MainViewModel: ViewModel() {

    val repository = ShopItemRepositoryImpl

    val getShopListUseCase = GetShopListUseCase(repository)
    val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopItems = MutableLiveData<List<ShopItem>>()


    fun getItems(): LiveData<List<ShopItem>>{
        shopItems.value = getShopListUseCase.getShopList()
        return shopItems
    }


    fun removeItem(shopItem: ShopItem){
        removeShopItemUseCase.removeShopItem(shopItem)
        getItems()
    }

    fun changeEnable(shopItem: ShopItem){
        val newItem = shopItem.copy(isEnable = !shopItem.isEnable)
        editShopItemUseCase.editShopItem(newItem)
        getItems()
    }

}