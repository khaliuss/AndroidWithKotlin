package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shoplist.data.room.ShopItemDatabase
import com.example.shoplist.data.room.ShopListMapper
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopItemRepository

class ShopItemRepositoryImpl(application: Application) : ShopItemRepository {

    private val database = ShopItemDatabase.getInstance(application)
    private val mapper = ShopListMapper()
    override suspend fun addShopItem(shopItem: ShopItem) {
        database.shopItemDao().addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        database.shopItemDao().deleShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        addShopItem(shopItem)
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val md = database.shopItemDao().getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(md)
    }

    override fun getShoplist(): LiveData<List<ShopItem>> =
        database.shopItemDao().getShopList().map {
            mapper.mapListDbModelToEntityList(it)
        }

}