package com.example.shoplist.domain

import android.os.Debug

data class ShopItem(
    var title: String,
    var count: Int,
    var isEnable: Boolean,
    var id:Int = UNDEFINED
){

    companion object{
        const val UNDEFINED = -1
    }
}
