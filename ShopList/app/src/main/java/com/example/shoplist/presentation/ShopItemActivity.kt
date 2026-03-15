package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoplist.R

class ShopItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
    }

    companion object{
        const val SHOP_ITEM_ID = "ShopItemId"

        fun addShopItemMode(context: Context): Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            return intent
        }

        fun editShopItemMode(context: Context,shopItemId:Int): Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SHOP_ITEM_ID,shopItemId)
            return intent
        }
    }
}