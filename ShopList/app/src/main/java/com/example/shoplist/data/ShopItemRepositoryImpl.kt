package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopItemRepository
import kotlin.random.Random

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopList = sortedSetOf<ShopItem>({o1,o2 ->
        o1.id.compareTo(o2.id)
    })

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    init {
        for (i in 0..15){
            val item = ShopItem(
                title = "Name $i",
                count = Random.nextInt(100),
                isEnable = Random.nextBoolean()
            )
            addShopItem(item)
        }
    }
    private var autoIncrementId = 0
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("Element with id $shopItemId not found")
    }

    override fun getShoplist(): LiveData<List<ShopItem>>{
        return shopListLD
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }

}