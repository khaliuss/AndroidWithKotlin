package com.example.shoplist.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItemDBModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title: String,
    var count: Int,
    val isEnable: Boolean = true,
)

