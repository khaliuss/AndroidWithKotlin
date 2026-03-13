package com.example.shoplist.domain

data class ShopItem(
    var title: String,
    var count: Int,
    var isEnable: Boolean,
    var id:Int = UNDEFINED_ID
){

    companion object{
        const val UNDEFINED_ID = -1
    }
}
